package com.interworks.inspektar.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.BaseActivity;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity{

    ArrayList<VideoListTestData> videoList;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvTestVideos = findViewById(R.id.rvVideoList);

        videoList = VideoListTestData.createTestVideoList(20);

        HomeAdapter adapter = new HomeAdapter(videoList);
        rvTestVideos.setAdapter(adapter);
        rvTestVideos.setLayoutManager(new LinearLayoutManager(this));

    }
}
