package com.interworks.inspektar.di.modules;

import com.interworks.inspektar.camera.RecordVideoFragmentProvider;
import com.interworks.inspektar.camera.view.CameraActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = RecordVideoFragmentProvider.class)
    abstract CameraActivity bindCameraActivity();
}
