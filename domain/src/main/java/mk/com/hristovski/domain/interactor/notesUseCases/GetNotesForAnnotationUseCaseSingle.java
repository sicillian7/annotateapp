package mk.com.interworks.domain.interactor.notesUseCases;

import java.util.List;
import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetNotesForAnnotationUseCaseSingle extends UseCaseSingle<List<NoteEntity>, GetNotesForAnnotationUseCaseSingle.Params> {

    public GetNotesForAnnotationUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<NoteEntity>> buildUseCaseSingle(Params params) {
        return mLocalRepository.getNotesForAnnotation(params.getAnnotationId());
    }

    public class Params {

        private int annotationId;

        public Params(int annotationId) {
            this.annotationId = annotationId;
        }

        public int getAnnotationId() {
            return annotationId;
        }
    }
}
