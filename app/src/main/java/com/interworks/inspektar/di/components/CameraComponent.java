package com.interworks.inspektar.di.components;

import android.app.Activity;

import com.interworks.inspektar.camera.view.CameraActivity;
import com.interworks.inspektar.camera.view.RecordVideoFragment;
import com.interworks.inspektar.di.modules.ActivityModule;
import com.interworks.inspektar.di.modules.CameraModule;
import com.interworks.inspektar.di.scopes.PerActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = {DomainComponent.class}, modules = {ActivityModule.class, CameraModule.class})
public interface CameraComponent {
    void inject(CameraActivity act);
    void inject(RecordVideoFragment frag);
}
