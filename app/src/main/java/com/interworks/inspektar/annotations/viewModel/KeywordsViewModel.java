package com.interworks.inspektar.annotations.viewModel;

import com.interworks.inspektar.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;
import mk.com.interworks.domain.interactor.favoriteUseCases.GetAllFavoritesUseCaseSingle;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForCategoryUseCaseSingle;
import mk.com.interworks.domain.model.KeywordEntity;

public class KeywordsViewModel extends BaseViewModel{

    private GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCaseSingle;
    private GetAllFavoritesUseCaseSingle mGetAllFavoritesUseCaseSingle;

    @Inject
    public KeywordsViewModel(GetKeywordsForCategoryUseCaseSingle getKeywordsForCategoryUseCaseSingle, GetAllFavoritesUseCaseSingle getAllFavoritesUseCaseSingle) {
        this.mGetKeywordsForCategoryUseCaseSingle = getKeywordsForCategoryUseCaseSingle;
        this.mGetAllFavoritesUseCaseSingle = getAllFavoritesUseCaseSingle;
    }


    public void loadAllFavorites(DisposableSingleObserver<List<KeywordEntity>> observer){

    }
    public void getKeywordsForCategory(DisposableSingleObserver<List<KeywordEntity>> observer, GetKeywordsForCategoryUseCaseSingle.Params params){
        mGetKeywordsForCategoryUseCaseSingle.execute(observer, params);
    }

    private static class KeywordsForCategoryObserver extends DisposableSingleObserver<List<KeywordEntity>>{

        @Override
        public void onSuccess(List<KeywordEntity> keywordEntities) {

        }

        @Override
        public void onError(Throwable e) {

        }
    }
}
