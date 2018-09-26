package com.interworks.inspektar.camera.viewModel;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.interworks.inspektar.base.BaseViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import mk.com.interworks.domain.interactor.favoriteUseCases.GetAllFavoritesUseCaseSingle;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForFavoriteUseCaseSingle;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.model.KeywordEntity;
import timber.log.Timber;

public class CameraViewModel extends BaseViewModel{

    private static final String TAG = CameraViewModel.class.getName();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<KeywordEntity> mKeywords;
    private GetAllFavoritesUseCaseSingle mGetAllFavoritesUseCaseSingle;
    private GetKeywordsForFavoriteUseCaseSingle mGetKeywordsForFavoriteUseCase;

    @Inject
    public CameraViewModel(GetKeywordsForFavoriteUseCaseSingle getKeywordsForFavoriteUseCase, GetAllFavoritesUseCaseSingle getAllFavoritesUseCaseSingle) {
        this.mGetKeywordsForFavoriteUseCase = getKeywordsForFavoriteUseCase;
        this.mGetAllFavoritesUseCaseSingle = getAllFavoritesUseCaseSingle;
        init();
    }

    private void init(){
       mGetAllFavoritesUseCaseSingle.getObservableUsecase(null).flatMap(lsFavorites -> {
           if(lsFavorites.size() > 0){
               long id = 0;
               for (FavoriteEntity item : lsFavorites) {
                   if(item.isSelected()){
                       id = item.getId();
                   }
               }
               return mGetKeywordsForFavoriteUseCase.getObservableUsecase(new GetKeywordsForFavoriteUseCaseSingle.Params(id));
           }else{
               return mGetKeywordsForFavoriteUseCase.getObservableUsecase(new GetKeywordsForFavoriteUseCaseSingle.Params(0));
           }
       })
       .subscribeWith(new KeywordsObserver(this));
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
            Timber.d("error retrieving keywords for favorite");
        }
    }
}
