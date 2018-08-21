package mk.com.interworks.domain.interactor.videoUseCases;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetVideoByIdUseCaseSingle extends UseCaseSingle<VideoEntity, GetVideoByIdUseCaseSingle.Params> {

    public GetVideoByIdUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo,threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<VideoEntity> buildUseCaseSingle(Params params) {
        return mLocalRepository.getVideoById(params.getmId());
    }

    public class Params {
        private int mId;

        public Params(int mId) {
            this.mId = mId;
        }

        public int getmId() {
            return mId;
        }
    }
}
