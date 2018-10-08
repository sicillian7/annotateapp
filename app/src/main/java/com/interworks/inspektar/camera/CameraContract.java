package com.interworks.inspektar.camera;

import android.graphics.Rect;

import com.interworks.inspektar.camera.utils.AutoFitTextureView;
import com.interworks.inspektar.camera.view.CameraPreview;

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
