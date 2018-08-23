package com.interworks.inspektar.camera.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.interworks.inspektar.R;
import com.interworks.inspektar.camera.CameraService;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class RecordVideoFragment extends Fragment {

    private static final String TAG = "Camera2VideoFragment";
    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };


    CameraService mCameraService;

    private Unbinder unbinder;
    private Activity mActivity;
    private boolean isRecording;
//    @BindView(R.id.mTextureView)
//    AutoFitTextureView textureView;
    @BindView(R.id.btnRecord)
    ImageButton mButtonVideo;
    @BindView(R.id.camera_preview)
    FrameLayout mCameraPreview;

    public RecordVideoFragment() {
    }

    public static RecordVideoFragment newInstance(){
        return new RecordVideoFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.camera_record_fragment, container, false);
        unbinder = ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();

        if (!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions();
            return;
        }
        if (mActivity.isFinishing()) {
            return;
        }

        mCameraService = new CameraService(mActivity);
        mCameraPreview.addView(mCameraService.appendCameraPreview());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCameraService.releaseCamera();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.btnRecord)
    void onRecordClick(View view){
        if (mActivity != null) {
            if (isRecording) {
                mButtonVideo.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_btn_rec));
                mCameraService.stopRecordingVideo();
            }else{
                mButtonVideo.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_btn_stop));
                mCameraService.startRecordingVideo();
            }
            isRecording = !isRecording;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    /**
     * Gets whether you should show UI with rationale for requesting permissions.
     *
     * @param permissions The permissions your app wants to request.
     * @return Whether you can show permission rationale UI.
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        if (mActivity != null) {
            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Requests permissions needed for recording video.
     */
    private void requestVideoPermissions() {
        if (mActivity != null) {
            if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {
                new ConfirmationDialog().show(getChildFragmentManager(), FRAGMENT_DIALOG);
            } else {
                ActivityCompat.requestPermissions(mActivity, VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult");
        if (requestCode == REQUEST_VIDEO_PERMISSIONS) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        CameraRecordFragment.ErrorDialog.newInstance(mActivity.getResources().getString(R.string.permission_request))
                                .show(getChildFragmentManager(), FRAGMENT_DIALOG);
                        break;
                    }
                }
            } else {
                CameraRecordFragment.ErrorDialog.newInstance(getString(R.string.permission_request))
                        .show(getChildFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasPermissionsGranted(String[] permissions) {
        if (mActivity != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(mActivity, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


//    public static class CameraActionListener implements CameraKitEventCallback<CameraKitVideo> {
//
//        private WeakReference<Activity> weakContext;
//
//        public CameraActionListener(Activity c) {
//            weakContext = new WeakReference<>(c);
//        }
//
//        @Override
//        public void callback(CameraKitVideo cameraKitVideo) {
//            Activity mActivity = weakContext.get();
//            if (mActivity != null) {
//                File video = cameraKitVideo.getVideoFile();
//                if (Utils.isExternalStorageWritable()) {
//                    try {
//                        exportFile(video, Utils.getPublicAlbumStorageDir("InspektAR"));
//                        Toast.makeText(mActivity, "File saved to: " + Utils.getPublicAlbumStorageDir("InspektAR").getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(mActivity, "Failed saving file", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//
//        private File exportFile(File src, File dst) throws IOException {
//
//            //if folder does not exist
//            if (!dst.exists()) {
//                if (!dst.mkdir()) {
//                    return null;
//                }
//            }
//
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            File expFile = new File(dst.getPath() + File.separator + "Video_" + timeStamp + ".mp4");
//            FileChannel inChannel = null;
//            FileChannel outChannel = null;
//
//            try {
//                inChannel = new FileInputStream(src).getChannel();
//                outChannel = new FileOutputStream(expFile).getChannel();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            try {
//                inChannel.transferTo(0, inChannel.size(), outChannel);
//            } finally {
//                if (inChannel != null)
//                    inChannel.close();
//                if (outChannel != null)
//                    outChannel.close();
//            }
//
//            return expFile;
//        }
//    }

    public static class ConfirmationDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_request)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            requestPermissions(VIDEO_PERMISSIONS,
                                    REQUEST_VIDEO_PERMISSIONS);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    parent.getActivity().finish();
                                }
                            })
                    .create();
        }

    }
}