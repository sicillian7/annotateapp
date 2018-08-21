package com.interworks.inspektar.di.modules;

import android.content.Context;

import com.interworks.inspektar.di.scopes.ApplicationScope;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import mk.com.interworks.data.LocalDataSource;
import mk.com.interworks.domain.repository.LocalDataRepository;

@Module
public class PersistenceModule {

    @ApplicationScope
    @Provides
    LocalDataRepository provideLocalDataRepository(Context context){
        return new LocalDataSource(context);
    }
}
