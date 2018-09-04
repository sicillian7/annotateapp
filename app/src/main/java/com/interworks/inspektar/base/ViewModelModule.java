package com.interworks.inspektar.base;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.interworks.inspektar.annotations.viewModel.KeywordsViewModel;
import com.interworks.inspektar.annotations.viewModel.VideoRecordViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(VideoRecordViewModel.class)
    abstract ViewModel bindVideoRecordViewModel(VideoRecordViewModel userViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(KeywordsViewModel.class)
    abstract ViewModel bindKeywordsViewModel(KeywordsViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
