package com.hristovski.inspektar.camera.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.hristovski.inspektar.R;
import com.hristovski.inspektar.annotations.viewModel.AnnotationViewModel;
import com.hristovski.inspektar.databinding.CustomSaveVideoDialogBinding;

import java.lang.ref.WeakReference;

import mk.com.interworks.domain.model.VideoEntity;

public class SaveVideoDialog extends Dialog {

    public static final String TAG = SaveVideoDialog.class.getName();
    public interface OnActionListener{
        void onSave(VideoEntity viewModel);
        void onDiscard();
        void displayError(String err);
    }

    CustomSaveVideoDialogBinding binding;
    private OnActionListener mListener;
    private Context mContext;
    private VideoEntity model;
    private ClickHandlers handlers;

    public SaveVideoDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SaveVideoDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected SaveVideoDialog(@NonNull Context context, boolean cancelable, @Nullable DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        binding = (CustomSaveVideoDialogBinding)DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.custom_save_video_dialog, null, false);
        setContentView(binding.getRoot());
        handlers = new ClickHandlers(this);
        binding.setHandlers(handlers);
    }

    public void setModel(VideoEntity m){
        model = m;
    }

    public void setOnActionListener(OnActionListener l){
        mListener = l;
    }

    public static class ClickHandlers{

        private WeakReference<SaveVideoDialog> weakDialog;

        public ClickHandlers(SaveVideoDialog d) {
            weakDialog = new WeakReference<>(d);
        }

        public void onSaveClicked(View view){
            SaveVideoDialog d = weakDialog.get();
            if (d != null && d.mListener != null) {
                String title = d.binding.txtVideoName.getText().toString();
                if(title.length() > 0){
                    d.model.setName(title);
                    d.model.setDescription(d.binding.txtVideoDescription.getText().toString());
                    d.mListener.onSave(d.model);
                }else{
                    d.mListener.displayError("Video title can not be empty");
                }

            }
        }

        public void onDiscardClicked(View view){
            SaveVideoDialog d = weakDialog.get();
            if (d != null && d.mListener != null) {
                d.mListener.onDiscard();
            }
        }
    }
}
