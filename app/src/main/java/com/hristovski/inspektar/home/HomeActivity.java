package com.hristovski.inspektar.home;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hristovski.inspektar.R;
import com.hristovski.inspektar.camera.view.CameraActivity;
import com.hristovski.inspektar.camera.view.RecordVideoFragment;
import com.hristovski.inspektar.databinding.ActivityHomeBinding;
import com.hristovski.inspektar.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity{

    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String FRAGMENT_DIALOG = "dialog";

    private static final String[] VIDEO_PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
    };


    private ArrayList<VideoListTestData> videoList;
    private Toolbar mTopToolbar;

    ActivityHomeBinding binding;

    final Fragment homeFragment = new HomeFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;

    @BindView(R.id.camera_button)
    Button camera_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        binding =  binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        mTopToolbar = findViewById(R.id.toolbarTop);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);

        fm.beginTransaction().add(R.id.main_container,homeFragment, "1").commit();



        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        binding.cameraButton.setOnClickListener(new OnCameraClickListener(this));
    }

    private boolean shouldShowRequestPermissionRationale(String[] permissions) {

            for (String permission : permissions) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                    return true;
                }
            }

        return false;
    }

    private void requestVideoPermissions() {

            if (shouldShowRequestPermissionRationale(VIDEO_PERMISSIONS)) {
                new ConfirmationDialog().show(getSupportFragmentManager(), FRAGMENT_DIALOG);
            } else {
                ActivityCompat.requestPermissions(this, VIDEO_PERMISSIONS, REQUEST_VIDEO_PERMISSIONS);
            }

    }

    public static class ConfirmationDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_request)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> requestPermissions(VIDEO_PERMISSIONS,
                            REQUEST_VIDEO_PERMISSIONS))
                    .setNegativeButton(android.R.string.cancel,
                            (dialog, which) -> getActivity().finish())
                    .create();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
       Log.d("kurac", "onRequestPermissionsResult");
        if (requestCode == REQUEST_VIDEO_PERMISSIONS) {
            if (grantResults.length == VIDEO_PERMISSIONS.length) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        RecordVideoFragment.ErrorDialog.newInstance(getResources().getString(R.string.permission_request))
                                .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
                        break;
                    }
                }
                CameraActivity.startActivity(this);
            } else {
                RecordVideoFragment.ErrorDialog.newInstance(getString(R.string.permission_request))
                        .show(getSupportFragmentManager(), FRAGMENT_DIALOG);
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        }
    }

    /*@Override
    protected int getContentView() {
        return R.layout.activity_home;
    }*/

    public static class OnCameraClickListener implements View.OnClickListener{

        private WeakReference<HomeActivity> weakAct;

        public OnCameraClickListener(HomeActivity act) {
            weakAct = new WeakReference<>(act);
        }

        @Override
        public void onClick(View view) {
            HomeActivity act = weakAct.get();
            if (act != null) {
                if (!Utils.hasPermissionsGranted(act,VIDEO_PERMISSIONS)) {
                    act.requestVideoPermissions();
                    return;
                }else{
                    CameraActivity.startActivity(act);
                }
            }
        }
    }
    public static class ClickHandlers{

    }
}
