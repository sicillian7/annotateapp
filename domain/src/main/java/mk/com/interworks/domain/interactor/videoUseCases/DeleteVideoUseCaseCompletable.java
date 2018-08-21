package mk.com.interworks.domain.interactor.videoUseCases;

import io.reactivex.Completable;
import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseCompletable;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class DeleteVideoUseCaseCompletable extends UseCaseCompletable<DeleteVideoUseCaseCompletable.Params> {

    public DeleteVideoUseCaseCompletable(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params) {
        return mLocalRepository.removeVideo(params.getmVideo());
    }

    public class Params {

        private VideoEntity mVideo;

        public VideoEntity getmVideo() {
            return mVideo;
        }
    }
}
