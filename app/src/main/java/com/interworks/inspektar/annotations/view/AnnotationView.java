package com.interworks.inspektar.annotations.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.viewModel.AnnotationViewModel;
import com.interworks.inspektar.databinding.CustomAnnotationViewBinding;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.KeywordEntity;

public class AnnotationView extends FrameLayout {

    CustomAnnotationViewBinding binding;
    private Context mContext;
    AnnotationViewModel viewModel;
    AnnotationEntity entity;
    KeywordEntity keyword;


    public AnnotationView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public AnnotationView(Context context,  @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public AnnotationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.custom_annotation_view, this, true);
        getViewTreeObserver().addOnGlobalLayoutListener(new OnDrawFinishedObserver(this));
    }

    public void setViewModel(AnnotationViewModel vm){
        viewModel = vm;
        entity = vm.getEntity();
        keyword = vm.getKeyword();

        int resId = mContext.getResources().getIdentifier(mContext.getApplicationContext().getPackageName()+":drawable/" + keyword.getResPath(), null, null);
        Picasso.get().load(resId).into(binding.imgAnnotation);
        binding.txtAnnotation.setText(keyword.getName());

    }

    private void displayView(int width, int height){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        binding.background.setLayoutParams(params);
        binding.holder.setVisibility(View.VISIBLE);
    }

    private static class OnDrawFinishedObserver implements ViewTreeObserver.OnGlobalLayoutListener{

        private WeakReference<AnnotationView> weakHolder;

        public OnDrawFinishedObserver(AnnotationView p) {
            weakHolder = new WeakReference<>(p);
        }

        @Override
        public void onGlobalLayout() {
            FrameLayout parent = weakHolder.get();
            if (parent != null) {
                parent.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ((AnnotationView) parent).displayView(parent.getWidth(), parent.getHeight());
            }
        }
    }
}
