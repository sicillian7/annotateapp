package com.interworks.inspektar.camera.view;

import android.content.Context;
import android.content.Intent;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;

public class CameraActivity extends BaseActivity{

    public static void startActivity(Context context){
        Intent i = new Intent(context, CameraActivity.class);
        context.startActivity(i);
    }
    @Override
    protected int getContentView() {
        return R.layout.camera_activity;
    }
}
