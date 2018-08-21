package mk.com.interworks.domain.interactor.keywordUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetAllKeywordsUseCaseSingle extends UseCaseSingle<List<KeywordEntity>, Void> {

    public GetAllKeywordsUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<KeywordEntity>> buildUseCaseSingle(Void aVoid) {
        return mLocalRepository.getAllKeywords();
    }
}
