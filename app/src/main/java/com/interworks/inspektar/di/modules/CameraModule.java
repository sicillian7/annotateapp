package com.interworks.inspektar.di.modules;


import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.interworks.inspektar.R;
import com.interworks.inspektar.annotations.adapter.KeywordsAdapter;
import com.interworks.inspektar.annotations.view.AnnotationGridDialog;
import com.interworks.inspektar.annotations.viewModel.KeywordsViewModel;
import com.interworks.inspektar.camera.CameraContract;
import com.interworks.inspektar.camera.CameraService;
import com.interworks.inspektar.camera.view.RecordVideoFragment;
import com.interworks.inspektar.di.scopes.PerActivity;

import java.util.HashMap;
import java.util.Map;

import dagger.Module;
import dagger.Provides;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForCategoryUseCaseSingle;

@Module(includes = {DomainModule.class})
public class CameraModule {

    @PerActivity
    @Provides
    RecordVideoFragment providesRecordVideoFragment(){
        return RecordVideoFragment.newInstance();
    }

    @PerActivity
    @Provides
    RecyclerView.LayoutManager providesLayoutManager(Activity c){
        return new LinearLayoutManager(c);
    }

    @PerActivity
    @Provides
    KeywordsAdapter providesKeywordsAdapter(){
        return new KeywordsAdapter();
    }

    @PerActivity
    @Provides
    AnnotationGridDialog providesKeywordsDialog(Activity c, RecyclerView.LayoutManager layoutManager, KeywordsAdapter adapter){
        return new AnnotationGridDialog(c.findViewById(R.id.camera_preview ),adapter, layoutManager);
    }

    @PerActivity
    @Provides
    CameraContract providesCameraService(Activity c){
        return new CameraService(c);
    }

//    @PerActivity
//    @Provides
//    KeywordsViewModel providesKeywordsViewModel(GetKeywordsForCategoryUseCaseSingle getKeywordsUsecase){
//        return new KeywordsViewModel(getKeywordsUsecase);
//    }

//    @PerActivity
//    @Provides
//    Map<Class<ViewModel>, ? extends ViewModel> providesMapViewModels(KeywordsViewModel kwViewModel){
//        Map<Class<ViewModel>, ? extends ViewModel> map = new HashMap<>();
//        map.put(KeywordsViewModel.class, kwViewModel)
//    }
}
