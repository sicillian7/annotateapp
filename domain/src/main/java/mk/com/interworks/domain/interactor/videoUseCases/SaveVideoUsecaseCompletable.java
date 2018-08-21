package mk.com.interworks.domain.interactor.videoUseCases;

import io.reactivex.Completable;

import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseCompletable;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class SaveVideoUsecaseCompletable extends UseCaseCompletable<SaveVideoUsecaseCompletable.Params> {

    public SaveVideoUsecaseCompletable(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo,threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params){
        return mLocalRepository.saveVideo(params.getVideo());
    }

    public class Params {

        private VideoEntity video;

        public Params(VideoEntity video) {
            this.video = video;
        }

        public VideoEntity getVideo() {
            return video;
        }
    }
}
