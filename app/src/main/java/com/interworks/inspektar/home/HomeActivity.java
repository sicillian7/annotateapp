package com.interworks.inspektar.home;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.interworks.inspektar.MainActivity;
import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;
import com.interworks.inspektar.base.BaseViewModel;
import com.interworks.inspektar.camera.view.CameraActivity;
import com.interworks.inspektar.databinding.ActivityHomeBinding;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity{

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

        binding.cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraActivity.startActivity(HomeActivity.this);
            }
        });
    }

    /*@Override
    protected int getContentView() {
        return R.layout.activity_home;
    }*/

    public static class ClickHandlers{

    }
}
