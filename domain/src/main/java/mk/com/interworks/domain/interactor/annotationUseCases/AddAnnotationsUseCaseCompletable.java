package mk.com.interworks.domain.interactor.annotationUseCases;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseCompletable;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class AddAnnotationsUseCaseCompletable extends UseCaseSingle<List<Long>,AddAnnotationsUseCaseCompletable.Params> {


    public AddAnnotationsUseCaseCompletable(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<Long>> buildUseCaseSingle(Params params) {
        return mLocalRepository.addAnnotationsForVideo(params.lsItems);
    }

    public static class Params {

        private List<AnnotationEntity> lsItems;

        public Params(List<AnnotationEntity> lsItems) {
            this.lsItems = lsItems;
        }
    }
}
