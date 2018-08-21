package mk.com.interworks.domain.interactor.videoUseCases;

import java.util.List;

import io.reactivex.Single;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.base.UseCaseSingle;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class GetAllVideosUseCaseSingle extends UseCaseSingle<List<VideoEntity>, Void> {

    public GetAllVideosUseCaseSingle(LocalDataRepository repo, ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        super(repo, threadExecutor, postExecutionThread);
    }

    @Override
    protected Single<List<VideoEntity>> buildUseCaseSingle(Void aVoid) {
        return mLocalRepository.getAllVideos();
    }
}
