package com.interworks.inspektar.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.interworks.inspektar.annotations.viewModel.KeywordsViewModel;
import com.interworks.inspektar.annotations.viewModel.VideoRecordViewModel;
import com.interworks.inspektar.base.ViewModelFactory;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(VideoRecordViewModel.class)
    abstract ViewModel bindVideoRecordViewModel(VideoRecordViewModel videoRecordViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(KeywordsViewModel.class)
    abstract ViewModel bindKeywordsViewModel(KeywordsViewModel keywordsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
