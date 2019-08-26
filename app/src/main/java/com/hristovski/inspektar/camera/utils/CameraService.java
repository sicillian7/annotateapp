package com.hristovski.inspektar.camera.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.OrientationEventListener;
import android.view.Surface;

import com.hristovski.inspektar.camera.CameraContract;
import com.hristovski.inspektar.camera.view.CameraPreview;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

@Deprecated
public class CameraService implements CameraContract {

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

    private OrientationManager mOrientationManager;

    private Integer mSensorOrientation;

    private Camera mCamera;
    private CameraPreview mCameraPreview;
    private Activity mContext;
    private MediaRecorder mMediaRecorder;
    private CamcorderProfile mCurrentProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
    private Rect mVideoArea;

    public CameraService(Activity c) {
        mContext = c;
    }

    public void setup(Camera cam, CameraPreview camPreview, OrientationManager om){
        try {
            mCamera = cam; // attempt to get a Camera instance
            mCameraPreview = camPreview;
            mOrientationManager = om;
            setDisplayOrientation();

            mVideoArea = calculateVideoArea();

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
    public AutoFitTextureView appendTexture() {
        return null;
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

    @Override
    public long getAnnotationStartTime() {
        return 0;
    }

    public boolean prepareVideoRecorder() throws IOException {

        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(mCurrentProfile);
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
       // camera.setDisplayOrientation(result);
        Method downPolymorphic;
        try
        {
            downPolymorphic = camera.getClass().getMethod("setDisplayOrientation", new Class[] { int.class });
            if (downPolymorphic != null)
                downPolymorphic.invoke(camera, new Object[] { result });
        }
        catch (Exception e1)
        {
        }
    }

    private String getVideoFilePath(Context context) {
        final File dir = context.getExternalFilesDir(null);
        return (dir == null ? "" : (dir.getAbsolutePath() + "/"))
                + System.currentTimeMillis() + ".mp4";
    }

    private Rect calculateVideoArea() {

        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int screenHeight = metrics.heightPixels;

        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

        double widthScale = 0, heightScale = 0;
        if (profile.videoFrameWidth != 0)
            widthScale = (double) screenWidth
                    / (double) profile.videoFrameWidth;
        if (profile.videoFrameHeight != 0)
            heightScale = (double) screenHeight
                    / (double) profile.videoFrameHeight;

        double scale = Math.min(widthScale, heightScale);

        int width = (int) (profile.videoFrameWidth * scale);
        int height = (int) (profile.videoFrameHeight * scale);
        int offsetY = (screenHeight - height) / 2;
        int offsetX = (screenWidth - width) / 2;

        Rect r = new Rect(offsetX, offsetY, width + offsetX, height + offsetY);
        return r;
    }

    @Override
    public Rect getVideoArea() {
        return mVideoArea;
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

    @Override
    public void prepareCamera() {

    }

    @Override
    public void stopBackgroundThread() {

    }
}
