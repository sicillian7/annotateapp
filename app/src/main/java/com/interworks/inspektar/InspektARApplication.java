package com.interworks.inspektar;

import android.app.Application;

import com.interworks.inspektar.di.components.ApplicationComponent;
import com.interworks.inspektar.di.components.DaggerApplicationComponent;
import com.interworks.inspektar.di.modules.ApplicationModule;

public class InspektARApplication extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    private void initApplicationComponent(){

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
