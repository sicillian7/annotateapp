package mk.com.interworks.domain.interactor.keywordUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetKeywordsForCategoryUseCaseSingle extends UseCaseSingle<List<KeywordEntity>, GetKeywordsForCategoryUseCaseSingle.Params> {

    public GetKeywordsForCategoryUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<KeywordEntity>> buildUseCaseSingle(Params params) {
        return mLocalRepository.getKeywordsForCategory(params.getmCategoryId());
    }

    public class Params {

        private int mCategoryId;

        public Params(int mCategoryId) {
            this.mCategoryId = mCategoryId;
        }

        public int getmCategoryId() {
            return mCategoryId;
        }
    }
}
