package mk.com.interworks.domain.interactor.videoUseCases;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetVideoIdByNameUseCaseSingle  extends UseCaseSingle<Integer, GetVideoIdByNameUseCaseSingle.Params> {

    public GetVideoIdByNameUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<Integer> buildUseCaseSingle(Params params) {
        return mLocalRepository.getVideoIdByName(params.getmVideoName());
    }

    public class Params {

        private String mVideoName;

        public Params(String mVideoName) {
            this.mVideoName = mVideoName;
        }

        public String getmVideoName() {
            return mVideoName;
        }
    }
}
