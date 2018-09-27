package com.interworks.inspektar.home;

import android.app.Activity;
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
import android.widget.Toast;

import com.interworks.inspektar.MainActivity;
import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity{

    private ArrayList<VideoListTestData> videoList;
    private Toolbar mTopToolbar;

    final Fragment homeFragment = new HomeFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = homeFragment;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        mTopToolbar = findViewById(R.id.toolbarTop);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);

        fm.beginTransaction().add(R.id.main_container,homeFragment, "1").commit();



        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_home;
    }
}
