package mk.com.interworks.domain.interactor.categoryUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.CategoryEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetAllCategoriesUseCaseSingle  extends UseCaseSingle<List<CategoryEntity>, Void> {

    public GetAllCategoriesUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<CategoryEntity>> buildUseCaseSingle(Void params) {
        return mLocalRepository.getAllCategories();
    }
}
