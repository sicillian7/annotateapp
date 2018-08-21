package mk.com.interworks.domain.interactor.notesUseCases;

import io.reactivex.Completable;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseCompletable;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class AddNoteUseCaseCompletable extends UseCaseCompletable<AddNoteUseCaseCompletable.Params> {

    public AddNoteUseCaseCompletable(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params) {
        return mLocalRepository.addNoteForAnnotation(params.getmNote());
    }

    public class Params {

        private NoteEntity mNote;

        public Params(NoteEntity mNote) {
            this.mNote = mNote;
        }

        public NoteEntity getmNote() {
            return mNote;
        }
    }
}
