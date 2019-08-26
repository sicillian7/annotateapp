package com.hristovski.inspektar.camera;

import com.hristovski.inspektar.camera.view.RecordVideoFragment;
import com.hristovski.inspektar.di.modules.DomainModule;
import com.hristovski.inspektar.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class RecordVideoFragmentProvider {

    @ContributesAndroidInjector(modules = {RecordVideoFragmentModule.class})
    abstract RecordVideoFragment provideBlogFragmentFactory();
}