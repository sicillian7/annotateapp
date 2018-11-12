package com.interworks.inspektar.annotations.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.viewModel.AnnotationViewModel;
import com.interworks.inspektar.databinding.CustomAnnotationViewBinding;
import com.interworks.inspektar.utils.ScreenUtils;
import com.interworks.inspektar.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.KeywordEntity;

public class AnnotationView extends FrameLayout {

    public interface OnViewInitialized{
        void onInitialized(int width, int height, AnnotationViewModel vm, AnnotationView annotationView);
    }

    CustomAnnotationViewBinding binding;
    private Context mContext;
    AnnotationViewModel viewModel;
    AnnotationEntity entity;
    KeywordEntity keyword;

    private OnViewInitialized mOnViewInitializedListener;


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

    public void setViewModel(AnnotationViewModel vm, OnViewInitialized l){
        viewModel = vm;
        entity = vm.getEntity();
        keyword = vm.getKeyword();

        mOnViewInitializedListener = l;

        int resId = mContext.getResources().getIdentifier(mContext.getApplicationContext().getPackageName()+":drawable/" + keyword.getResPath(), null, null);
        Picasso.get().load(resId).into(binding.imgAnnotation);
        binding.txtAnnotation.setText(keyword.getName());
       // binding.tailUp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_annotation_tail_up));
       // binding.tail_down.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_annotation_tail_down));
    }

    private void initView(int width, int height){
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height - ViewUtils.dpToPx(10));
        binding.background.setLayoutParams(params);
        LinearLayout.LayoutParams tailParams = new LinearLayout.LayoutParams(ViewUtils.dpToPx(20),ViewUtils.dpToPx(20));

        if(ScreenUtils.getScreenWidth(mContext) < entity.getX() + width){
            tailParams.setMargins(Math.round(entity.getX() + width) - ScreenUtils.getScreenWidth(mContext) + ViewUtils.dpToPx(10),0,0,0);
        }
        else if((entity.getX() - width) < 0){

            int margin = 0;
            if(ViewUtils.pxToDp(entity.getX()) < 10){
                margin = Math.round(entity.getX()) + ViewUtils.dpToPx(5);
            }else{
                margin = Math.round(entity.getX());
            }
            tailParams.setMargins(margin,0,0,0);

        } else{
            tailParams.setMargins((width/2) - ViewUtils.dpToPx(10),0,0,0);
        }

        //is the starting Y point positioned in the lower part of the screen - the annotation's tail should be faced down
        if(entity.getY() > (ScreenUtils.getScreenHeight(mContext)/2)){
            tailParams.topMargin = ViewUtils.dpToPx(-2.1f);
            binding.tailDown.setLayoutParams(tailParams);
            binding.tailDown.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_annotation_tail_down));
            binding.tailDown.setVisibility(View.VISIBLE);
        }else{
            binding.tailUp.setLayoutParams(tailParams);
            binding.tailUp.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.item_annotation_tail_up));
            binding.tailUp.setVisibility(View.VISIBLE);
        }

        if (mOnViewInitializedListener != null) {
            mOnViewInitializedListener.onInitialized(width, height, viewModel, this);
        }

      //  binding.holder.setVisibility(View.VISIBLE);
    }

    public void show(){
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
                ((AnnotationView) parent).initView(parent.getWidth(), parent.getHeight());
            }
        }
    }
}
