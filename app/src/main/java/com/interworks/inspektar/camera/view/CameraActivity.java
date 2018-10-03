package com.interworks.inspektar.camera.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.interworks.inspektar.BR;
import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;
import com.interworks.inspektar.base.BaseViewModel;
import com.interworks.inspektar.base.ViewModelFactory;
import com.interworks.inspektar.camera.viewModel.CameraViewModel;
import com.interworks.inspektar.databinding.CameraActivityBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class CameraActivity extends AppCompatActivity implements HasSupportFragmentInjector{

    private static final String TAG = CameraActivity.class.getName();

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelFactory mViewModelFactory;
    CameraViewModel viewModel;
    CameraActivityBinding binding;

//    @Override
//    public int getBindingVariable() {
//        return BR.viewModel;
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.camera_activity;
//    }
//
//    @Override
//    public CameraViewModel getViewModel() {
//        return viewModel;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        Log.d(TAG, "OnCreate CameraAcitivity");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.camera_activity);
        viewModel = ViewModelProviders.of(this, mViewModelFactory).get(CameraViewModel.class);
        if (savedInstanceState == null)
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(binding.container.getId(), RecordVideoFragment.newInstance())
                    .commitAllowingStateLoss();
    }

    public static void startActivity(Context context){
        Intent i = new Intent(context, CameraActivity.class);
        context.startActivity(i);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CameraActivity.class);
    }
}
