package com.interworks.inspektar.home;

import android.media.Image;

import com.interworks.inspektar.R;

import java.util.ArrayList;
import java.util.Date;

public class VideoListTestData {

    private String mTitle;
    private String mDate;
    private String mHour;
    private int mImage;


    public VideoListTestData(String title, String date, String hour, int image) {
        mTitle = title;
        mDate = date;
        mHour = hour;
        mImage = image;
    }

    public int getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getHour() {
        return mHour;
    }

    public static ArrayList<VideoListTestData> createTestVideoList(int numberOfVideos){

        ArrayList<VideoListTestData> videos = new ArrayList<>();

        for (int i = 1; i <= numberOfVideos; i++) {
            videos.add(new VideoListTestData("Video Title ", "12 August 2018 ", "| 20:35", R.drawable.list_test));
        }

        return videos;

    }
}
