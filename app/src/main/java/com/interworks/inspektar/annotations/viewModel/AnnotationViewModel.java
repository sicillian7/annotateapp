package com.interworks.inspektar.annotations.viewModel;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;

import mk.com.interworks.domain.model.AnnotationEntity;

public class AnnotationViewModel {

    private float x,y;
    private AnnotationEntity entity;

  //  private Rect mVideoRect;
    private View mCameraView;

    public AnnotationViewModel() {
    }

    public AnnotationViewModel(float x, float y, View cameraView) {
        entity = new AnnotationEntity();
        mCameraView = cameraView;
        PointF p = absoluteToRelative(x,y);
        entity.setX(p.x);
        entity.setY(p.y);
     //   mVideoRect = videoRectangle;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Point relativeToAbsolute(float x, float y) {

        int dx = (int) (mCameraView.getWidth() * x);
        int dy = (int) (mCameraView.getHeight() * y);

        return new Point(dx, dy);
    }

    public PointF absoluteToRelative(float x, float y) {

        float dx = x / mCameraView.getWidth();
        float dy = y / mCameraView.getWidth();

        return new PointF(dx, dy);
    }

    public AnnotationEntity getEntity() {
        return entity;
    }

    public void setEntity(AnnotationEntity entity) {
        this.entity = entity;
    }
}
