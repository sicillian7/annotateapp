package com.interworks.inspektar.camera.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;

public class CameraActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(R.id.container, RecordVideoFragment.newInstance());
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
