package com.interworks.inspektar.camera;

import com.interworks.inspektar.camera.view.CameraPreview;

public interface CameraContract {

    void startRecordingVideo();
    void stopRecordingVideo();
    void releaseCamera();
    CameraPreview appendCameraPreview();
    void setDisplayOrientation();
}
