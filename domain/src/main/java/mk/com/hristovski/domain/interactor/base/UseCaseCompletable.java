package mk.com.interworks.domain.interactor.base;

import org.assertj.core.util.Preconditions;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.repository.LocalDataRepository;

public abstract class UseCaseCompletable<Params> extends UseCase<Void,Params>  {

    protected LocalDataRepository mLocalRepository;

    public UseCaseCompletable(LocalDataRepository repo,ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mLocalRepository = repo;
    }

    public void execute(DisposableCompletableObserver observer, Params params) {
        Preconditions.checkNotNull(observer);
        final Completable observable = this.buildUseCaseCompletable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    public Completable getObservableUsecase(Params params){
        return this.buildUseCaseCompletable(params).subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
    }

    protected abstract Completable buildUseCaseCompletable(Params params);
}
