package com.interworks.inspektar.camera.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.interworks.inspektar.BR;
import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.view.AnnotationGridDialog;
import com.interworks.inspektar.annotations.view.AnnotationView;
import com.interworks.inspektar.annotations.view.KeywordsDialog;
import com.interworks.inspektar.annotations.viewModel.AnnotationViewModel;
import com.interworks.inspektar.base.BaseFragment;
import com.interworks.inspektar.base.BaseViewModel;
import com.interworks.inspektar.base.ViewModelFactory;
import com.interworks.inspektar.camera.CameraContract;
import com.interworks.inspektar.camera.viewModel.CameraViewModel;
import com.interworks.inspektar.databinding.CameraRecordFragmentBinding;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import mk.com.interworks.domain.model.VideoEntity;

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
    @Inject
    CameraActivity mActivity;
    private boolean isRecording;

    @Inject
    KeywordsDialog keywordsDialog;
    @Inject
    SaveVideoDialog saveVideoDialog;
    @Inject
    ViewModelFactory mFactory;

    private ClickHandlers handlers;
    private View mRootView;
    private CameraViewModel mCameraViewModel;
    CameraRecordFragmentBinding binding;

    public RecordVideoFragment() {
    }

    public static RecordVideoFragment newInstance(){
        return new RecordVideoFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.camera_record_fragment, container, false);
        mRootView = binding.getRoot();
        return mRootView;
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
        handlers = new ClickHandlers(this);
        binding.setHandlers(handlers);
        mCameraViewModel = ViewModelProviders.of(this, mFactory).get(CameraViewModel.class);
        binding.cameraPreview.addView(mCameraService.appendTexture());
        binding.cameraPreview.setOnTouchListener(new OnTouchListener(this));
        keywordsDialog.setListener(new KeywordsDialogActionListener(this));
        saveVideoDialog.setModel(mCameraViewModel.getTempVideoData());
        saveVideoDialog.setOnActionListener(new SaveVideoDialogActionListener(this));
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

    public void onRecordClick(View view){
        if (mActivity != null) {
            if (isRecording) {
                binding.btnRecord.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_btn_rec));
                mCameraService.stopRecordingVideo();
                saveVideoDialog.show();
            }else{
                binding.btnRecord.setImageDrawable(ContextCompat.getDrawable(mActivity, R.drawable.ic_btn_stop));
                mCameraService.startRecordingVideo();
            }
            isRecording = !isRecording;
        }
    }

    public void displayKeywordsDialog(AnnotationViewModel m, float x, float y){
        keywordsDialog.setViewModel(m);
        keywordsDialog.notifyDatasetChanged(mCameraViewModel.getKeywords());
        keywordsDialog.show();
    }

    @Override
    public void onDestroyView() {
//        if (keywordsDialog != null) {
//            keywordsDialog.unbindViews();
//        }
        super.onDestroyView();
    }

    public void displayError(String msg){
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
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

    public class ClickHandlers {

        WeakReference<RecordVideoFragment> weakFragment;

        public ClickHandlers(RecordVideoFragment f) {
            weakFragment = new WeakReference<>(f);
        }

        public void onRecordClick(View view){
            RecordVideoFragment f = weakFragment.get();
            if (f != null) {
                f.onRecordClick(view);
            }
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
            if(frag.isRecording && motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                AnnotationViewModel m = new AnnotationViewModel(motionEvent.getX(), motionEvent.getY(), frag.mCameraService.appendTexture(),frag.mCameraService.getAnnotationStartTime());
                frag.displayKeywordsDialog(m, motionEvent.getX(), motionEvent.getY());
            }else{
                return false;
            }

            return true;
        }
    }

    private void attachAnnotationView(AnnotationViewModel vm){
        mCameraViewModel.addAnnotation(vm.getEntity());
        AnnotationView annotationView = new AnnotationView(getActivity());
        annotationView.setViewModel(vm);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,(int)getActivity().getResources().getDimension(R.dimen.annotation_height));
        params.leftMargin = Math.round(vm.getEntity().getX());
        params.topMargin = Math.round(vm.getEntity().getY());
        binding.cameraPreview.addView(annotationView, params);
        detachAnnotationView(annotationView);
    }

    private void detachAnnotationView(AnnotationView view){
        WeakReference<AnnotationView> wRef = new WeakReference<>(view);
        WeakReference<FrameLayout> wFrag = new WeakReference<>(binding.cameraPreview);
        getActivity().runOnUiThread(() -> {
            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                AnnotationView v = wRef.get();
                FrameLayout layout = wFrag.get();
                if (v != null && layout != null) {
                    layout.removeView(v);
                }
            }, 1000);
        });
    }

    private static class KeywordsDialogActionListener implements KeywordsDialog.OnActionListener{
        private WeakReference<RecordVideoFragment> weakFrag;

        public KeywordsDialogActionListener(RecordVideoFragment frag) {
            weakFrag = new WeakReference<>(frag);
        }

        @Override
        public void onKeywordSelected(AnnotationViewModel vm) {
            RecordVideoFragment frag = weakFrag.get();
            if (frag != null) {
                frag.keywordsDialog.dismiss();
                frag.attachAnnotationView(vm);
            }
        }
    }

    private static class SaveVideoDialogActionListener implements SaveVideoDialog.OnActionListener{

        private WeakReference<RecordVideoFragment> weakFrag;

        public SaveVideoDialogActionListener(RecordVideoFragment f) {
            weakFrag = new WeakReference<>(f);
        }

        @Override
        public void onSave(VideoEntity model) {
            RecordVideoFragment f = weakFrag.get();
            if (f != null) {
                f.mCameraViewModel.saveVideoData();
                f.saveVideoDialog.dismiss();
            }
        }

        @Override
        public void onDiscard() {
            RecordVideoFragment f = weakFrag.get();
            if (f != null) {
                f.saveVideoDialog.dismiss();
            }
        }

        @Override
        public void displayError(String err) {
            RecordVideoFragment f = weakFrag.get();
            if (f != null) {
                f.displayError(err);
                f.saveVideoDialog.dismiss();
            }
        }
    }
}
