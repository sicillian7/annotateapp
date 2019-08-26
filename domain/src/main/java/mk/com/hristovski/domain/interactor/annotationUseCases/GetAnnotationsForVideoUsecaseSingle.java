package mk.com.interworks.domain.interactor.annotationUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetAnnotationsForVideoUsecaseSingle extends UseCaseSingle<List<AnnotationEntity>, GetAnnotationsForVideoUsecaseSingle.Params> {

    public GetAnnotationsForVideoUsecaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<AnnotationEntity>> buildUseCaseSingle(Params params) {
        return mLocalRepository.getAnnotationsForVideo(params.getmVideoId());
    }

    public class Params {

        private int mVideoId;

        public Params(int mVideoId) {
            this.mVideoId = mVideoId;
        }

        public int getmVideoId() {
            return mVideoId;
        }
    }
}
