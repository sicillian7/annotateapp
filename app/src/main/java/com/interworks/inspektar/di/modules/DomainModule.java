package com.interworks.inspektar.di.modules;

import dagger.Module;
import dagger.Provides;
import mk.com.interworks.domain.executor.PostExecutionThread;
import mk.com.interworks.domain.executor.ThreadExecutor;
import mk.com.interworks.domain.interactor.annotationUseCases.AddAnnotationUseCaseCompletable;
import mk.com.interworks.domain.interactor.annotationUseCases.GetAnnotationsForVideoUsecaseSingle;
import mk.com.interworks.domain.interactor.annotationUseCases.RemoveAnnotationUseCaseSCompletable;
import mk.com.interworks.domain.interactor.categoryUseCases.GetAllCategoriesUseCaseSingle;
import mk.com.interworks.domain.interactor.keywordUseCases.GetAllKeywordsUseCaseSingle;
import mk.com.interworks.domain.interactor.keywordUseCases.GetKeywordsForCategoryUseCaseSingle;
import mk.com.interworks.domain.interactor.notesUseCases.AddNoteUseCaseCompletable;
import mk.com.interworks.domain.interactor.notesUseCases.GetNotesForAnnotationUseCaseSingle;
import mk.com.interworks.domain.interactor.notesUseCases.RemoveNoteUseCaseCompletable;
import mk.com.interworks.domain.interactor.videoUseCases.DeleteVideoUseCaseCompletable;
import mk.com.interworks.domain.interactor.videoUseCases.GetAllVideosUseCaseSingle;
import mk.com.interworks.domain.interactor.videoUseCases.GetVideoByIdUseCaseSingle;
import mk.com.interworks.domain.interactor.videoUseCases.GetVideoIdByNameUseCaseSingle;
import mk.com.interworks.domain.interactor.videoUseCases.SaveVideoUsecaseCompletable;
import mk.com.interworks.domain.repository.LocalDataRepository;

@Module
public class DomainModule {

    @Provides
    AddAnnotationUseCaseCompletable providesAddAnnotationUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new AddAnnotationUseCaseCompletable(repo, workerThread, mainThread);
    }
    @Provides
    GetAnnotationsForVideoUsecaseSingle providesGetAnnotationsForVideoUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetAnnotationsForVideoUsecaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    RemoveAnnotationUseCaseSCompletable providesRemoveAnnotationUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new RemoveAnnotationUseCaseSCompletable(repo, workerThread, mainThread);
    }
    @Provides
    GetAllCategoriesUseCaseSingle providesGetCategoriesUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetAllCategoriesUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    GetAllKeywordsUseCaseSingle providesGetKeywordsUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetAllKeywordsUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    GetKeywordsForCategoryUseCaseSingle providesGetKeywordsForCategoryUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetKeywordsForCategoryUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    AddNoteUseCaseCompletable providesAddNoteUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new AddNoteUseCaseCompletable(repo, workerThread, mainThread);
    }
    @Provides
    GetNotesForAnnotationUseCaseSingle providesGetNotesForAnnotation(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetNotesForAnnotationUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    RemoveNoteUseCaseCompletable providesRemoveNoteUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new RemoveNoteUseCaseCompletable(repo, workerThread, mainThread);
    }
    @Provides
    SaveVideoUsecaseCompletable providesSaveVideoUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new SaveVideoUsecaseCompletable(repo, workerThread, mainThread);
    }
    @Provides
    GetAllVideosUseCaseSingle providesGetVideosUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetAllVideosUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    GetVideoByIdUseCaseSingle providesGetVideoByIdUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetVideoByIdUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    GetVideoIdByNameUseCaseSingle providesGetVideoIdByNameUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new GetVideoIdByNameUseCaseSingle(repo, workerThread, mainThread);
    }
    @Provides
    DeleteVideoUseCaseCompletable providesDeleteVideoUseCase(LocalDataRepository repo, ThreadExecutor workerThread, PostExecutionThread mainThread){
        return new DeleteVideoUseCaseCompletable(repo, workerThread, mainThread);
    }
}
