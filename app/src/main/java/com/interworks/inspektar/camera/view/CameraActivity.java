package com.interworks.inspektar.camera.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;
import com.interworks.inspektar.di.components.CameraComponent;
import com.interworks.inspektar.di.components.DaggerCameraComponent;
import com.interworks.inspektar.di.components.DaggerDomainComponent;
import com.interworks.inspektar.di.components.DomainComponent;
import com.interworks.inspektar.di.modules.ActivityModule;
import com.interworks.inspektar.di.modules.CameraModule;
import com.interworks.inspektar.di.modules.DomainModule;

public class CameraActivity extends BaseActivity{

    private CameraComponent mCameraComponent;
    private DomainComponent mDomainComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDomainComponent = DaggerDomainComponent.builder()
                .applicationComponent(getApplicationComponent())
                .domainModule(new DomainModule())
                .build();
        mCameraComponent = DaggerCameraComponent.builder()
                .domainComponent(mDomainComponent)
                .activityModule(new ActivityModule(this))
                .cameraModule(new CameraModule())
                .build();
        RecordVideoFragment frag = RecordVideoFragment.newInstance();
        mCameraComponent.inject(frag);
        addFragment(R.id.container, frag);
    }

    public static void startActivity(Context context){
        Intent i = new Intent(context, CameraActivity.class);
        context.startActivity(i);
    }
    @Override
    protected int getContentView() {
        return R.layout.camera_activity;
    }
}
