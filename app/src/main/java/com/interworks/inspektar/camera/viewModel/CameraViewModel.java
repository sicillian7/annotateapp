package com.interworks.inspektar.camera.viewModel;

import android.util.Log;

import com.interworks.inspektar.base.BaseViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import mk.com.interworks.domain.interactor.annotationUseCases.AddAnnotationUseCaseCompletable;
import mk.com.interworks.domain.interactor.annotationUseCases.AddAnnotationsUseCaseCompletable;
import mk.com.interworks.domain.interactor.favoriteUseCases.GetAllFavoritesUseCaseSingle;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForFavoriteUseCaseSingle;
import mk.com.interworks.domain.interactor.videoUseCases.SaveVideoUseCaseSingle;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.model.VideoEntity;
import timber.log.Timber;

public class CameraViewModel extends BaseViewModel{

    private static final String TAG = CameraViewModel.class.getName();

    boolean isProgressDialogVisible = false;

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<KeywordEntity> mKeywords;
    private GetAllFavoritesUseCaseSingle mGetAllFavoritesUseCaseSingle;
    private GetKeywordsForFavoriteUseCaseSingle mGetKeywordsForFavoriteUseCase;
    private SaveVideoUseCaseSingle mSaveVideoUseCase;
    private AddAnnotationsUseCaseCompletable mAddAnnotationsUseCase;
    private VideoEntity tempVideoData;
    private List<AnnotationEntity> tempAnnotationsByVideo;

    @Inject
    public CameraViewModel(GetKeywordsForFavoriteUseCaseSingle getKeywordsForFavoriteUseCase, GetAllFavoritesUseCaseSingle getAllFavoritesUseCaseSingle,
                           SaveVideoUseCaseSingle saveVideoUsecase, AddAnnotationsUseCaseCompletable addAnnotationsUseCaseCompletable) {
        this.mGetKeywordsForFavoriteUseCase = getKeywordsForFavoriteUseCase;
        this.mGetAllFavoritesUseCaseSingle = getAllFavoritesUseCaseSingle;
        this.mSaveVideoUseCase = saveVideoUsecase;
        this.mAddAnnotationsUseCase = addAnnotationsUseCaseCompletable;
        init();
    }

    private void init(){

        tempAnnotationsByVideo = new ArrayList<>();
        tempVideoData = new VideoEntity();

        loadKeywords();
    }

    private void loadKeywords(){
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

    public void saveVideoData(){
        mSaveVideoUseCase.getObservableUsecase(new SaveVideoUseCaseSingle.Params(tempVideoData)).flatMap(id -> {
            tempVideoData.setId(id);
            for (AnnotationEntity annotation : tempAnnotationsByVideo) {
                annotation.setVideoId(id);
            }
            return mAddAnnotationsUseCase.getObservableUsecase(new AddAnnotationsUseCaseCompletable.Params(tempAnnotationsByVideo));
        }).subscribeWith(new SaveVideoObserver(this));
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.clear();
        mGetKeywordsForFavoriteUseCase.dispose();
        mAddAnnotationsUseCase.dispose();
        mSaveVideoUseCase.dispose();
        super.onCleared();
    }

    public List<KeywordEntity> getKeywords(){
        return mKeywords;
    }

    public VideoEntity getTempVideoData(){
        return tempVideoData;
    }

    public void setTempVideoData(VideoEntity m){
        tempVideoData = m;
    }

    public void addAnnotation(AnnotationEntity annotation){
        tempAnnotationsByVideo.add(annotation);
    }

    public void clearTempData(){
        Log.d(TAG, "Video saved to DB: id = " + tempVideoData.getId() + "Name = " + tempVideoData.getName() +  " Annotations: " + tempAnnotationsByVideo.toString());
        tempAnnotationsByVideo.clear();
        tempVideoData = new VideoEntity();
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
            CameraViewModel vm = weakCameraViewModel.get();
            if (vm != null) {
                Log.d(TAG, e.getMessage());
            }

        }
    }

    private static class SaveVideoObserver implements SingleObserver<List<Long>>{

        private WeakReference<CameraViewModel> weakViewModel;

        public SaveVideoObserver(CameraViewModel vm) {
            weakViewModel = new WeakReference<>(vm);
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onSuccess(List<Long> longs) {
            CameraViewModel vm = weakViewModel.get();
            if (vm != null) {
                vm.clearTempData();
            }
        }

        @Override
        public void onError(Throwable e) {
            CameraViewModel vm = weakViewModel.get();
            if (vm != null) {
                Log.d(TAG, e.getMessage());
            }
        }
    }
}
