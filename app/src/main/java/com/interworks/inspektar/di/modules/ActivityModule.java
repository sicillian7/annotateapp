package com.interworks.inspektar.di.modules;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.interworks.inspektar.di.scopes.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity provideContext(){
        return activity;
    }
}