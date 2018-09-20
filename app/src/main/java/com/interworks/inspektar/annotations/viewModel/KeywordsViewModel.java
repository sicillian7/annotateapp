package com.interworks.inspektar.annotations.viewModel;

import android.arch.lifecycle.ViewModel;

import com.interworks.inspektar.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForCategoryUseCaseSingle;
import mk.com.interworks.domain.model.KeywordEntity;

public class KeywordsViewModel extends BaseViewModel{

    private GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCaseSingle;

    @Inject
    public KeywordsViewModel(GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCaseSingle) {
        this.mGetKeywordsForCategoryUseCaseSingle = mGetKeywordsForCategoryUseCaseSingle;
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
