package mk.com.interworks.domain.interactor.favoriteUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetAllFavoritesUseCaseSingle extends UseCaseSingle<List<FavoriteEntity>, Void>{

    public GetAllFavoritesUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<FavoriteEntity>> buildUseCaseSingle(Void aVoid) {
        return mLocalRepository.getAllFavorites();
    }
}
