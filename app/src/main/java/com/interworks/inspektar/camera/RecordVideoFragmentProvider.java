package com.interworks.inspektar.camera;

import com.interworks.inspektar.camera.view.RecordVideoFragment;
import com.interworks.inspektar.di.modules.DomainModule;
import com.interworks.inspektar.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecordVideoFragmentProvider {

    @ContributesAndroidInjector(modules = {RecordVideoFragmentModule.class})
    abstract RecordVideoFragment provideBlogFragmentFactory();
}