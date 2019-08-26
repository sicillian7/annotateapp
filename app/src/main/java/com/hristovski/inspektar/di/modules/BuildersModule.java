package com.hristovski.inspektar.di.modules;

import com.hristovski.inspektar.camera.RecordVideoFragmentProvider;
import com.hristovski.inspektar.camera.view.CameraActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = RecordVideoFragmentProvider.class)
    abstract CameraActivity bindCameraActivity();
}
