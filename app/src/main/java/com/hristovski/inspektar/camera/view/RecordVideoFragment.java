package com.hristovski.inspektar.camera.view;

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

import com.hristovski.inspektar.BR;
import com.hristovski.inspektar.R;
import com.hristovski.inspektar.annotations.view.AnnotationGridDialog;
import com.hristovski.inspektar.annotations.view.AnnotationView;
import com.hristovski.inspektar.annotations.view.KeywordsDialog;
import com.hristovski.inspektar.annotations.viewModel.AnnotationViewModel;
import com.hristovski.inspektar.base.BaseFragment;
import com.hristovski.inspektar.base.BaseViewModel;
import com.hristovski.inspektar.base.ViewModelFactory;
import com.hristovski.inspektar.camera.CameraContract;
import com.hristovski.inspektar.camera.viewModel.CameraViewModel;
import com.hristovski.inspektar.databinding.CameraRecordFragmentBinding;
import com.hristovski.inspektar.utils.ScreenUtils;
import com.hristovski.inspektar.utils.Utils;

import java.lang.ref.WeakReference;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import mk.com.interworks.domain.model.AnnotationEntity;
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
            if (keywordsDialog.isShowing()) {
                keywordsDialog.dismiss();
            }
            if (saveVideoDialog.isShowing()) {
                saveVideoDialog.dismiss();
            }
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
        binding.unbind();
        super.onDestroyView();
    }

    public void displayError(String msg){
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
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
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
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
        annotationView.setViewModel(vm, new OnAnnnotationInitListener(this));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,(int)getActivity().getResources().getDimension(R.dimen.annotation_height));
        binding.cameraPreview.addView(annotationView, params);
        detachAnnotationView(annotationView);
        annotationView = null;
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
                   // layout.removeView(v);
                    v = null;
                }
            }, 3000);
        });
    }

    public void onAnnotationAttached(int width, int height, AnnotationViewModel vm, AnnotationView view){
        if (getActivity() != null) {
            AnnotationEntity entity = vm.getEntity();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)view.getLayoutParams();
            if(ScreenUtils.getScreenWidth(getActivity()) < entity.getX() + width){
                params.leftMargin = ScreenUtils.getScreenWidth(getActivity()) - width;
            }
            else if((entity.getX() - width) < 0){
                params.rightMargin = width;
            }else{
                //params.leftMargin = Math.round(entity.getX())/2;
                params.leftMargin = Math.round(entity.getX())- width/2;
            }

            if(entity.getY() > (ScreenUtils.getScreenHeight(getActivity())/2)){
                params.topMargin = Math.round(entity.getY()) - height;
            }else{
                params.topMargin = Math.round(entity.getY());
            }

//            if(ScreenUtils.getScreenHeight(getActivity()) - (2*getActivity().getResources().getDimension(R.dimen.material_layout_app_bar_height)) < (entity.getY() + height)){
//                params.topMargin = Math.round(entity.getY()) - height;
//            }else{
//                params.topMargin = Math.round(entity.getY());
//            }

            view.setLayoutParams(params);
            view.show();
        }
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

    private static class OnAnnnotationInitListener implements AnnotationView.OnViewInitialized{

        private WeakReference<RecordVideoFragment> weakFrag;

        public OnAnnnotationInitListener(RecordVideoFragment f) {
            weakFrag = new WeakReference<>(f);
        }

        @Override
        public void onInitialized(int width, int height, AnnotationViewModel vm, AnnotationView view) {
            RecordVideoFragment frag = weakFrag.get();
            if (frag != null) {
                frag.onAnnotationAttached(width, height, vm, view);
            }
        }
    }
}
