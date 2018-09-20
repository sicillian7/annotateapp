package com.interworks.inspektar.camera.viewModel;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.ViewModel;

import com.interworks.inspektar.base.BaseViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForCategoryUseCaseSingle;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.CategoryEntity;
import mk.com.interworks.domain.model.KeywordEntity;

public class CameraViewModel extends BaseViewModel implements LifecycleObserver {

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<KeywordEntity> mKeywords;
    private GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCase;

    @Inject
    public CameraViewModel(GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCase) {
        this.mGetKeywordsForCategoryUseCase = mGetKeywordsForCategoryUseCase;
        init();

    }

    private void init(){

    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.clear();
        super.onCleared();
    }

    private static class KeywordsObserver implements SingleObserver<List<KeywordEntity>>{

        private WeakReference<CameraViewModel> weakCameraViewModel;

        public KeywordsObserver(CameraViewModel cvm) {
            weakCameraViewModel = new WeakReference<>(cvm);
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<KeywordEntity> keywordEntities) {
            CameraViewModel vm = weakCameraViewModel.get();
            if (vm != null) {
                vm.mKeywords = keywordEntities;
            }
        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
