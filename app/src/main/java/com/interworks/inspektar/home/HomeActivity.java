package com.interworks.inspektar.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity{

    private ArrayList<VideoListTestData> videoList;
    private Toolbar mTopToolbar;

    /*@Override
    protected int getContentView() {
        return R.layout.activity_main;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTopToolbar = findViewById(R.id.toolbarTop);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);



        RecyclerView rvTestVideos = findViewById(R.id.rvVideoList);

        videoList = VideoListTestData.createTestVideoList(20);

        HomeAdapter adapter = new HomeAdapter(videoList);
        rvTestVideos.setAdapter(adapter);
        rvTestVideos.setLayoutManager(new LinearLayoutManager(this));

    }
}
