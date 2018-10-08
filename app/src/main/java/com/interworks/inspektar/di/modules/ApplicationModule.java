package com.interworks.inspektar.di.modules;

import android.app.Application;
import android.content.Context;

import com.interworks.inspektar.InspektARApplication;
import com.interworks.inspektar.UIThread;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import mk.com.interworks.data.LocalDataSource;
import mk.com.interworks.data.executor.JobExecutor;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.repository.LocalDataRepository;

@Module(includes = {ViewModelModule.class, DomainModule.class})
public class ApplicationModule {

    @Singleton
    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
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

    @Singleton
    @Provides
    LocalDataRepository provideLocalDataRepository(Context context){
        return new LocalDataSource(context);
    }
}
