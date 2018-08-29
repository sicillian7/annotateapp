package com.interworks.inspektar.camera;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.OrientationEventListener;
import android.view.Surface;

import com.interworks.inspektar.camera.view.CameraPreview;

import java.io.File;
import java.io.IOException;

public class CameraService implements CameraContract{

    private static final String TAG = CameraService.class.getName();
    private static final int SENSOR_ORIENTATION_DEFAULT_DEGREES = 90;
    private static final int SENSOR_ORIENTATION_INVERSE_DEGREES = 270;
    private static final SparseIntArray DEFAULT_ORIENTATIONS = new SparseIntArray();
    private static final SparseIntArray INVERSE_ORIENTATIONS = new SparseIntArray();


    static {
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_0, 90);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_90, 0);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_180, 270);
        DEFAULT_ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    static {
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_0, 270);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_90, 180);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_180, 90);
        INVERSE_ORIENTATIONS.append(Surface.ROTATION_270, 0);
    }

    private String mNextVideoAbsolutePath;
    private int mDisplayRotation;
    private int mCameraDisplayOrientation;

    private int mOrientation = OrientationEventListener.ORIENTATION_UNKNOWN;
    private int mLastRawOrientation;
    private OrientationManager mOrientationManager;

    private Integer mSensorOrientation;

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Activity mContext;
    private MediaRecorder mMediaRecorder;

    public CameraService(Activity c) {
        mContext = c;
        init();
    }

    private void init(){
        try {
            mCamera = Camera.open(); // attempt to get a Camera instance
            mCameraPreview = new CameraPreview(mContext, mCamera);
            mOrientationManager = new OrientationManager(mContext);
            setDisplayOrientation();

            android.hardware.camera2.CameraManager manager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
            if (manager != null) {
                String cameraId = manager.getCameraIdList()[0];
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);
                StreamConfigurationMap map = characteristics
                        .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                mSensorOrientation = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
            }

        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
            Log.d(TAG, "error opening camera");
        }
    }

    @Override
    public void startRecordingVideo() {
        try {
            if (prepareVideoRecorder()) {
                mOrientationManager.lockOrientation();
                mMediaRecorder.start();
            } else {
                releaseMediaRecorder();
            }
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaRecorder();
        }
    }

    @Override
    public void stopRecordingVideo() {
        mMediaRecorder.stop();  // stop the recording
        releaseMediaRecorder(); // release the MediaRecorder object
        mOrientationManager.unlockOrientation();
        mCamera.lock();
    }

    @Override
    public CameraPreview appendCameraPreview() {
        setCameraDisplayOrientation(mContext,0, mCamera);
        return mCameraPreview;
    }

    @Override
    public void setDisplayOrientation() {
        mDisplayRotation = CameraUtil.getDisplayRotation(mContext);
        mCameraDisplayOrientation = CameraUtil.getDisplayOrientation(mDisplayRotation, 0);
        // Change the camera display orientation
        if (mCamera != null) {
            mCamera.setDisplayOrientation(mCameraDisplayOrientation);
        }
    }

    public boolean prepareVideoRecorder() throws IOException {

        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        if (mNextVideoAbsolutePath == null || mNextVideoAbsolutePath.isEmpty()) {
            mNextVideoAbsolutePath = getVideoFilePath(mContext);
        }
        mMediaRecorder.setOutputFile(mNextVideoAbsolutePath);
        mMediaRecorder.setPreviewDisplay(mCameraPreview.getHolder().getSurface());


        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);
        int rotation = mContext.getWindowManager().getDefaultDisplay().getRotation();
        switch (mSensorOrientation) {
            case SENSOR_ORIENTATION_DEFAULT_DEGREES:
                mMediaRecorder.setOrientationHint(DEFAULT_ORIENTATIONS.get(rotation));
                break;
            case SENSOR_ORIENTATION_INVERSE_DEGREES:
                mMediaRecorder.setOrientationHint(INVERSE_ORIENTATIONS.get(rotation));
                break;
        }
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.d(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.d(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, android.hardware.Camera camera) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0: degrees = 0; break;
            case Surface.ROTATION_90: degrees = 90; break;
            case Surface.ROTATION_180: degrees = 180; break;
            case Surface.ROTATION_270: degrees = 270; break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }


    private String getVideoFilePath(Context context) {
        final File dir = context.getExternalFilesDir(null);
        return (dir == null ? "" : (dir.getAbsolutePath() + "/"))
                + System.currentTimeMillis() + ".mp4";
    }

    private void releaseMediaRecorder(){
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();   // clear recorder configuration
            mMediaRecorder.release(); // release the recorder object
            mMediaRecorder = null;
            mCamera.lock();           // lock camera for later use
        }
    }

    public void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }
}
