package mk.com.interworks.domain.interactor.base;

import org.assertj.core.util.Preconditions;

import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.repository.LocalDataRepository;

public abstract class UseCaseSingle<T, Params> extends UseCase<T, Params> {

    protected LocalDataRepository mLocalRepository;

    public UseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mLocalRepository = repo;
    }

    public void execute(DisposableSingleObserver<T> observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Single<T> observable = this.buildUseCaseSingle(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    protected abstract Single<T> buildUseCaseSingle(Params params);
}
