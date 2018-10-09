package mk.com.interworks.domain.repository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.CategoryEntity;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.model.VideoEntity;

public interface LocalDataRepository {

    Single<Long> saveVideo(VideoEntity video);
   // Completable saveVideo(VideoEntity video);
    Single<List<VideoEntity>> getAllVideos();
    Single<VideoEntity> getVideoById(long id);
    Completable removeVideo(VideoEntity video);
    Single<Integer> getVideoIdByName(String videoName);
    Completable addAnnotation(AnnotationEntity annotation);
    Completable removeAnnotation(AnnotationEntity annotation);
    Single<List<Long>> addAnnotationsForVideo(List<AnnotationEntity> annotations);
    Single<List<AnnotationEntity>> getAnnotationsForVideo(long videoId);
    Single<List<CategoryEntity>> getAllCategories();
    Single<List<KeywordEntity>> getAllKeywords();
    Single<List<KeywordEntity>> getKeywordsForCategory(long categoryId);
    Single<List<NoteEntity>> getNotesForAnnotation(long annotationId);
    Completable addNoteForAnnotation(NoteEntity note);
    Completable removeNotes(List<NoteEntity> lsNotes);
    Single<List<FavoriteEntity>> getAllFavorites();
    Single<List<KeywordEntity>> getKeywordsForFavorite(long favoriteId);
}
