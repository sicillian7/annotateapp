package com.interworks.inspektar.annotations.viewModel;

import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForCategoryUseCaseSingle;
import mk.com.interworks.domain.model.KeywordEntity;

public class KeywordsViewModel extends ViewModel{

//    private GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCaseSingle;
//
//    @Inject
//    public KeywordsViewModel(GetKeywordsForCategoryUseCaseSingle mGetKeywordsForCategoryUseCaseSingle) {
//        this.mGetKeywordsForCategoryUseCaseSingle = mGetKeywordsForCategoryUseCaseSingle;
//    }
//
//    public void getKeywordsForCategory(DisposableSingleObserver<List<KeywordEntity>> observer, GetKeywordsForCategoryUseCaseSingle.Params params){
//        mGetKeywordsForCategoryUseCaseSingle.execute(observer, params);
//    }
}
