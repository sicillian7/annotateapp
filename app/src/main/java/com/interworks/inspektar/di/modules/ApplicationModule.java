package com.interworks.inspektar.di.modules;

import android.app.Application;
import android.content.Context;

import com.interworks.inspektar.InspektARApplication;
import com.interworks.inspektar.di.scopes.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;

@Module
public class ApplicationModule {

    private InspektARApplication mApplication;

    public ApplicationModule(InspektARApplication mApplication) {
        this.mApplication = mApplication;
    }

    @ApplicationScope
    @Provides
    public Context providesApplication(){
        return mApplication;
    }

    @ApplicationScope
    @Provides
    ThreadExecutor provideThreadExecutor(ThreadExecutor jobExecutor) {
        return jobExecutor;
    }

    @ApplicationScope
    @Provides
    PostExecutionThread providePostExecutionThread(PostExecutionThread uiThread) {
        return uiThread;
    }
}
