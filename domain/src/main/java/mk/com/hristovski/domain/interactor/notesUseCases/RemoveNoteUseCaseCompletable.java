package mk.com.interworks.domain.interactor.notesUseCases;

import java.util.List;

import io.reactivex.Completable;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseCompletable;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class RemoveNoteUseCaseCompletable extends UseCaseCompletable<RemoveNoteUseCaseCompletable.Params> {

    public RemoveNoteUseCaseCompletable(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Completable buildUseCaseCompletable(Params params) {
        return mLocalRepository.removeNotes(params.getmNotes());
    }

    public class Params {

        private List<NoteEntity> mNotes;

        public Params(List<NoteEntity> mNotes) {
            this.mNotes = mNotes;
        }

        public List<NoteEntity> getmNotes() {
            return mNotes;
        }
    }
}
