package mk.com.interworks.domain.interactor.keywordUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetKeywordsForFavoriteUseCaseSingle extends UseCaseSingle<List<KeywordEntity>, GetKeywordsForFavoriteUseCaseSingle.Params> {


    public GetKeywordsForFavoriteUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<KeywordEntity>> buildUseCaseSingle(Params params) {
        return mLocalRepository.getKeywordsForFavorite(params.mFavoriteId);
    }

    public static class Params {

        private long mFavoriteId;

        public Params(long mFavoriteId) {
            this.mFavoriteId = mFavoriteId;
        }

        public long getFavoriteId() {
            return mFavoriteId;
        }
    }
}
