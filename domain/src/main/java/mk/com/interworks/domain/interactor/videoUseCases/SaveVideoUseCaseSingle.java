package mk.com.interworks.domain.interactor.videoUseCases;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class SaveVideoUseCaseSingle extends UseCaseSingle<Long,SaveVideoUseCaseSingle.Params> {


    public SaveVideoUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<Long> buildUseCaseSingle(Params params) {
        return mLocalRepository.saveVideo(params.mVideo);
    }

    public static class Params {
        private VideoEntity mVideo;

        public Params(VideoEntity mVideo) {
            this.mVideo = mVideo;
        }
    }
}
