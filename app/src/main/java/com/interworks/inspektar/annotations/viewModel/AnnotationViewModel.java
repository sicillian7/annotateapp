package com.interworks.inspektar.annotations.viewModel;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import mk.com.interworks.domain.model.AnnotationEntity;

public class AnnotationViewModel {

    private float x,y;
    private AnnotationEntity entity;

    private Rect mVideoRect;

    public AnnotationViewModel() {
    }

    public AnnotationViewModel(float x, float y, Rect videoRectangle) {
        entity = new AnnotationEntity();
        PointF p = absoluteToRelative(x,y);
        entity.setX(p.x);
        entity.setY(p.y);
        mVideoRect = videoRectangle;
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

        int dx = (int) (mVideoRect.width() * x);
        int dy = (int) (mVideoRect.height() * y);

        return new Point(dx, dy);
    }

    public PointF absoluteToRelative(float x, float y) {

        float dx = x / mVideoRect.width();
        float dy = y / mVideoRect.height();

        return new PointF(dx, dy);
    }

    public AnnotationEntity getEntity() {
        return entity;
    }

    public void setEntity(AnnotationEntity entity) {
        this.entity = entity;
    }

    public Rect getmVideoRect() {
        return mVideoRect;
    }

    public void setmVideoRect(Rect mVideoRect) {
        this.mVideoRect = mVideoRect;
    }
}
