package com.hristovski.inspektar.camera;

import android.graphics.Rect;

import com.hristovski.inspektar.camera.utils.AutoFitTextureView;
import com.hristovski.inspektar.camera.view.CameraPreview;

public interface CameraContract {

    void startRecordingVideo();
    void stopRecordingVideo();
    void releaseCamera();
    void prepareCamera();
    void stopBackgroundThread();
    CameraPreview appendCameraPreview();
    AutoFitTextureView appendTexture();
    void setDisplayOrientation();
    long getAnnotationStartTime();
    Rect getVideoArea();
}
