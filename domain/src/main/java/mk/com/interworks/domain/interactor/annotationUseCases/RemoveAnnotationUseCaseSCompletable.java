package mk.com.interworks.domain.interactor.annotationUseCases;

import io.reactivex.Completable;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseCompletable;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class RemoveAnnotationUseCaseSCompletable extends UseCaseCompletable<RemoveAnnotationUseCaseSCompletable.Params> {

    public RemoveAnnotationUseCaseSCompletable(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params) {
        return mLocalRepository.removeAnnotation(params.getmAnnotation());
    }

    public class Params {

        private AnnotationEntity mAnnotation;

        public Params(AnnotationEntity mAnnotation) {
            this.mAnnotation = mAnnotation;
        }

        public AnnotationEntity getmAnnotation() {
            return mAnnotation;
        }
    }
}
