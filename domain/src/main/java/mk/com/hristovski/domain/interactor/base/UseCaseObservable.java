package mk.com.interworks.domain.interactor.base;

import org.assertj.core.util.Preconditions;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;

public abstract class UseCaseObservable<T, P> extends UseCase<T, P> {

    UseCaseObservable(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
    }

    public void execute(DisposableObserver<T> observer, P params) {
        Preconditions.checkNotNull(observer);
        final Observable<T> observable = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.getScheduler());
        addDisposable(observable.subscribeWith(observer));
    }

    abstract Observable<T> buildUseCaseObservable(P params);
}
