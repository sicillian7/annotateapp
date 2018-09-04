package com.interworks.inspektar.camera;

import android.graphics.Rect;

import com.interworks.inspektar.camera.view.CameraPreview;

public interface CameraContract {

    void startRecordingVideo();
    void stopRecordingVideo();
    void releaseCamera();
    CameraPreview appendCameraPreview();
    void setDisplayOrientation();
    Rect getVideoArea();
}
