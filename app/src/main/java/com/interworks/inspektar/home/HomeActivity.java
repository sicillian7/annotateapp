package com.interworks.inspektar.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
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

public class HomeActivity extends AppCompatActivity{

    private ArrayList<VideoListTestData> videoList;
    private Toolbar mTopToolbar;

    FloatingActionButton fab;

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

        //BottomNavigationViewHelper.highlightSelectedItem((BottomNavigationMenuView)bottomNavigationView.getChildAt(0),HomeActivity.this);

        /*bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_search:
                        BottomNavigationViewHelper.highlightSelectedItem((BottomNavigationMenuView) bottomNavigationView.getChildAt(0),HomeActivity.this, 0);
                        break;
                    case R.id.action_import:
                        // open fragment 2
                        BottomNavigationViewHelper.highlightSelectedItem((BottomNavigationMenuView)bottomNavigationView.getChildAt(0),HomeActivity.this, 1);
                        break;
                    case R.id.action_add_new_video:
                        BottomNavigationViewHelper.highlightSelectedItem((BottomNavigationMenuView)bottomNavigationView.getChildAt(0),HomeActivity.this, 2);
                        break;
                    case R.id.action_export:
                        BottomNavigationViewHelper.highlightSelectedItem((BottomNavigationMenuView)bottomNavigationView.getChildAt(0),HomeActivity.this, 3);
                        break;
                    case R.id.action_favorite:
                        BottomNavigationViewHelper.highlightSelectedItem((BottomNavigationMenuView)bottomNavigationView.getChildAt(0),HomeActivity.this, 4);
                        break;
                }
                return false;
            }
        });*/




        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        //BottomNavigationMenuView menuView = (BottomNavigationMenuView) findViewById(R.id.bottom_navigation_view);

        /*for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }*/


        /*fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "FAB CLICKED", Toast.LENGTH_SHORT).show();
                bottomNavigationView.setSelectedItemId(R.id.action_add_new_video);
            }
        });*/

        RecyclerView rvTestVideos = findViewById(R.id.rvVideoList);

        videoList = VideoListTestData.createTestVideoList(20);

        HomeAdapter adapter = new HomeAdapter(videoList);
        rvTestVideos.setAdapter(adapter);
        rvTestVideos.setLayoutManager(new LinearLayoutManager(this));

    }
}
