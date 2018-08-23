package com.interworks.inspektar.di.modules;

import android.app.Application;
import android.content.Context;

import com.interworks.inspektar.InspektARApplication;
import com.interworks.inspektar.UIThread;
import com.interworks.inspektar.di.scopes.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.com.interworks.data.executor.JobExecutor;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;

@Module
public class ApplicationModule {

    private InspektARApplication mApplication;

    public ApplicationModule(InspektARApplication mApplication) {
        this.mApplication = mApplication;
    }

    @Singleton
    @Provides
    public Context providesApplication(){
        return mApplication;
    }

    @Singleton
    @Provides
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Singleton
    @Provides
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }
}
