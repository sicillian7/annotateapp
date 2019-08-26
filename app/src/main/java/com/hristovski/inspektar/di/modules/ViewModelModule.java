package com.hristovski.inspektar.di.modules;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.hristovski.inspektar.annotations.viewModel.KeywordsViewModel;
import com.hristovski.inspektar.annotations.viewModel.VideoRecordViewModel;
import com.hristovski.inspektar.base.ViewModelFactory;
import com.hristovski.inspektar.camera.viewModel.CameraViewModel;


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
    @ViewModelKey(CameraViewModel.class)
    abstract ViewModel bindKeywordsViewModel(CameraViewModel keywordsViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
