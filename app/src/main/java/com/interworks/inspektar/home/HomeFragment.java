package com.interworks.inspektar.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.interworks.inspektar.R;
import com.interworks.inspektar.base.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragment extends Fragment{

    private ItemTouchHelper mItemTouchHelper;
    private List<VideoListTestData> videoList;

    private HomeViewModel mHomeViewModel;

    @Inject
    ViewModelFactory mFactory;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        /*RecyclerView rvTestVideos = view.findViewById(R.id.rvVideoList);

        videoList = VideoListTestData.createTestVideoList(20);

        HomeAdapter adapter = new HomeAdapter(videoList);
        rvTestVideos.setAdapter(adapter);
        rvTestVideos.setLayoutManager(new LinearLayoutManager(getActivity()));
*/
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //mHomeViewModel = ViewModelProviders.of(this, mFactory).get(HomeViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rvVideoList);

        videoList = VideoListTestData.createTestVideoList(20);

        HomeAdapter adapter = new HomeAdapter(videoList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
