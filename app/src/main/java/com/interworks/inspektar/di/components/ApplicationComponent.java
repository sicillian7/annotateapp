package com.interworks.inspektar.di.components;

import android.app.Application;

import com.interworks.inspektar.InspektARApplication;
import com.interworks.inspektar.di.modules.ApplicationModule;
import com.interworks.inspektar.di.modules.BuildersModule;
import com.interworks.inspektar.di.modules.DomainModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;

@Singleton
@Component(modules = {
        /* Use AndroidInjectionModule.class if you're not using support library */
        AndroidSupportInjectionModule.class,
        ApplicationModule.class,
        BuildersModule.class,
        DomainModule.class})
public interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance Builder application(Application application);
        ApplicationComponent build();
    }

    void inject(InspektARApplication app);
}
