package com.interworks.inspektar.annotations.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.interworks.inspektar.annotations.base.IAnnotationsLayerContract;

import java.util.List;

import mk.com.interworks.domain.model.AnnotationEntity;

public class AnnotationsLayerView extends FrameLayout implements IAnnotationsLayerContract {

    private int screenWidth;
    public interface OnActionListener{
        void onAnnotationAdded(float x, float y);
    }

    public AnnotationsLayerView(@NonNull Context context) {
        super(context);
    }

    public AnnotationsLayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnnotationsLayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){

    }

    @Override
    public void displayAnnotation(AnnotationEntity model) {

    }

    @Override
    public void displayAnnotations(List<AnnotationEntity> lsAnnotations) {

    }
}
