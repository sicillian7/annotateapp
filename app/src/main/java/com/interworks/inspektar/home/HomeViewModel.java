package com.interworks.inspektar.home;

import com.interworks.inspektar.base.BaseViewModel;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import mk.com.interworks.domain.interactor.videoUseCases.GetAllVideosUseCaseSingle;
import mk.com.interworks.domain.model.VideoEntity;
import timber.log.Timber;

public class HomeViewModel extends BaseViewModel {

    public static final String TAG = HomeViewModel.class.getName();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private List<VideoEntity> mVideos;
    private GetAllVideosUseCaseSingle mGetAllVideosUseCaseSingle;


    @Inject
    public HomeViewModel(GetAllVideosUseCaseSingle getAllVideosUseCaseSingle) {
        this.mGetAllVideosUseCaseSingle = getAllVideosUseCaseSingle;
        init();
    }

    private void init() {

        this.mGetAllVideosUseCaseSingle.execute(new VideosObserver(this), null);

        /*mGetAllVideosUseCaseSingle.getObservableUsecase(null).flatMap(videoEntities ->
        {
            if (videoEntities.size() > 0 || videoEntities != null)
            {
                return mGetAllVideosUseCaseSingle.execute(new VideosObserver(this));
            }



        }).subscribeWith(new VideosObserver(this));*/
    }


    @Override
    protected void onCleared() {
        mCompositeDisposable.clear();
        super.onCleared();
    }

    public List<VideoEntity> getVideos() {
        return mVideos;
    }

    private static class VideosObserver extends DisposableSingleObserver<List<VideoEntity>> /*implements SingleObserver<List<VideoEntity>> */{

        private WeakReference<HomeViewModel> weakHomeViewModel;

        public VideosObserver(HomeViewModel homeViewModel) {
            weakHomeViewModel = new WeakReference<>(homeViewModel);
        }

        /*@Override
        public void onSubscribe(Disposable d) {

        }*/

        @Override
        public void onSuccess(List<VideoEntity> videoEntities) {

            HomeViewModel homeViewModel = weakHomeViewModel.get();
            if (homeViewModel != null) {
                homeViewModel.mVideos = videoEntities;
            }

        }

        @Override
        public void onError(Throwable e) {
            Timber.d("error retrieving videos");
        }
    }


}
