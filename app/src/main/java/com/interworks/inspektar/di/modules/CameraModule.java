package com.interworks.inspektar.di.modules;


import android.app.Activity;

import com.interworks.inspektar.camera.CameraContract;
import com.interworks.inspektar.camera.CameraService;
import com.interworks.inspektar.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class CameraModule {

    @PerActivity
    @Provides
    CameraContract providesCameraService(Activity c){
        return new CameraService(c);
    }
}
