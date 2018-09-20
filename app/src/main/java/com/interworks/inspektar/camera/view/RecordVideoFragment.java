package com.interworks.inspektar.camera.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.view.AnnotationGridDialog;
import com.interworks.inspektar.annotations.viewModel.AnnotationViewModel;
import com.interworks.inspektar.base.BaseFragment;
import com.interworks.inspektar.base.BaseViewModel;
import com.interworks.inspektar.base.ViewModelFactory;
import com.interworks.inspektar.camera.CameraContract;
import com.interworks.inspektar.camera.viewModel.CameraViewModel;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;

public class RecordVideoFragment extends DaggerFragment {

    private static final String TAG = RecordVideoFragment.class.getName();
    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };


    @Inject
    CameraContract mCameraService;
    Unbinder unbinder;
    @Inject
    CameraActivity mActivity;
    private boolean isRecording;
    @BindView(R.id.btnRecord)
    ImageButton mButtonVideo;
    @BindView(R.id.camera_preview)
    FrameLayout mCameraPreview;
    @Inject
    AnnotationGridDialog keywordsDialog;
    @Inject
    ViewModelFactory mFactory;

    private CameraViewModel mCameraViewModel;

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
        if (!hasPermissionsGranted(VIDEO_PERMISSIONS)) {
            requestVideoPermissions();
            return;
        }
        if (mActivity.isFinishing()) {
            return;
        }

        mCameraViewModel = ViewModelProviders.of(this, mFactory).get(CameraViewModel.class);
      //  mCameraPreview.addView(mCameraService.appendCameraPreview());
        mCameraPreview.addView(mCameraService.appendTexture());
        mCameraPreview.setOnTouchListener(new OnTouchListener(this));
        keywordsDialog.setListener(new KeywordsDialogActionListener(this));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCameraService.releaseCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mCameraService != null) {
            mCameraService.releaseCamera();
            mCameraService.stopBackgroundThread();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mCameraService != null) {
           mCameraService.prepareCamera();
        }
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

    public void displayKeywordsDialog(AnnotationViewModel m, float x, float y){
        keywordsDialog.setViewModel(m);
        keywordsDialog.show(x,y);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        if (keywordsDialog != null) {
            keywordsDialog.unbindViews();
        }
        super.onDestroyView();
    }

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
                        ErrorDialog.newInstance(mActivity.getResources().getString(R.string.permission_request))
                                .show(getChildFragmentManager(), FRAGMENT_DIALOG);
                        break;
                    }
                }
            } else {
                ErrorDialog.newInstance(getString(R.string.permission_request))
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

    public static class ConfirmationDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Fragment parent = getParentFragment();
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_request)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> requestPermissions(VIDEO_PERMISSIONS,
                            REQUEST_VIDEO_PERMISSIONS))
                    .setNegativeButton(android.R.string.cancel,
                            (dialog, which) -> parent.getActivity().finish())
                    .create();
        }

    }

    public static class ErrorDialog extends DialogFragment {

        private static final String ARG_MESSAGE = "message";

        public static ErrorDialog newInstance(String message) {
            ErrorDialog dialog = new ErrorDialog();
            Bundle args = new Bundle();
            args.putString(ARG_MESSAGE, message);
            dialog.setArguments(args);
            return dialog;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Activity activity = getActivity();
            return new AlertDialog.Builder(activity)
                    .setMessage(getArguments().getString(ARG_MESSAGE))
                    .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> activity.finish())
                    .create();
        }

    }

    private static class OnTouchListener implements View.OnTouchListener {

        private WeakReference<RecordVideoFragment> weakFrag;

        public OnTouchListener(RecordVideoFragment frag) {
            weakFrag = new WeakReference<>(frag);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            RecordVideoFragment frag = weakFrag.get();
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN && frag != null){
                AnnotationViewModel m = new AnnotationViewModel(motionEvent.getX(), motionEvent.getY(), frag.mCameraService.appendTexture());
                frag.displayKeywordsDialog(m, motionEvent.getX(), motionEvent.getY());
            }else{
                return false;
            }

            return true;
        }
    }

    private static class KeywordsDialogActionListener implements AnnotationGridDialog.OnActionListener{
        private WeakReference<RecordVideoFragment> weakFrag;

        public KeywordsDialogActionListener(RecordVideoFragment frag) {
            weakFrag = new WeakReference<>(frag);
        }

        @Override
        public void onKeywordSelected(AnnotationViewModel vm) {
            RecordVideoFragment frag = weakFrag.get();
            if (frag != null) {
                Toast.makeText(frag.mActivity, "ANNOTATED WITH KEYWORD WITH ID:" + String.valueOf(vm.getEntity().getKeywordId()), Toast.LENGTH_SHORT).show();
            }

        }
    }
}
