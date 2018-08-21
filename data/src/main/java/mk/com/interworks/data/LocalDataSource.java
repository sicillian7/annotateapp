package mk.com.interworks.data;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mk.com.interworks.data.db.LocalDB;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.CategoryEntity;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class LocalDataSource implements LocalDataRepository{

    private LocalDB _db;

    public LocalDataSource(Context context) {

        _db = Room.databaseBuilder(context,
                LocalDB.class, Constants.APP_DB_NAME).build();
    }

    @Override
    public Completable saveVideo(final VideoEntity video) {
        return  Completable.create( e -> {
            try {
                    _db.videoDAO().insert(video);
                    e.onComplete();
                }catch (Exception ex){
                   e.onError(ex);
                }
        });
    }

    @Override
    public Single<List<VideoEntity>> getAllVideos() {
        return Single.create(e -> {
            try {
                List<VideoEntity> lsItems = _db.videoDAO().getAllVideos();
                e.onSuccess(lsItems);
            }catch (Exception ex){
                e.onError(ex);
            }
        });
    }

    @Override
    public Single<VideoEntity> getVideoById(int id) {
        return  Single.create( e -> {
            try{
                VideoEntity video = _db.videoDAO().getVideoById(id);
                e.onSuccess(video);
            }catch (Exception ex){
                e.onError(ex);
            }
        });
    }

    @Override
    public Completable removeVideo(VideoEntity video) {
        return Completable.create(e -> {
            try {
                _db.videoDAO().delete(video);
                e.onComplete();
            }catch (Exception ex){
                e.onError(ex);
            }
        });
    }

    @Override
    public Single<Integer> getVideoIdByName(String videoName) {
        return null;
    }

    @Override
    public Completable addAnnotation(AnnotationEntity annotation) {
        return Completable.create(e -> {
            try{
                _db.annotationDAO().delete(annotation);
                e.onComplete();
            }catch (Exception ex){
                e.onError(ex);
            }
        });
    }

    @Override
    public Completable removeAnnotation(AnnotationEntity annotation) {
        return Completable.create(e -> {

            try {
                _db.annotationDAO().delete(annotation);
                e.onComplete();
            } catch (Exception e1) {
                e.onError(e1);
            }
        });
    }

    @Override
    public Single<List<AnnotationEntity>> getAnnotationsForVideo(int videoId) {
        return Single.create(e -> {
            try {
                List<AnnotationEntity> lsItems = _db.annotationDAO().getAnnotationsForVideo(videoId);
                e.onSuccess(lsItems);
            } catch (Exception e1) {
                e.onError(e1);
            }
        });
    }

    @Override
    public Single<List<CategoryEntity>> getAllCategories() {
        return null;
    }

    @Override
    public Single<List<KeywordEntity>> getAllKeywords() {
        return null;
    }

    @Override
    public Single<List<KeywordEntity>> getKeywordsForCategory(int categoryId) {
        return null;
    }

    @Override
    public Single<List<NoteEntity>> getNotesForAnnotation(int annotationId) {
        return null;
    }

    @Override
    public Completable addNoteForAnnotation(NoteEntity note) {
        return null;
    }

    @Override
    public Completable removeNotes(List<NoteEntity> lsNotes) {
        return null;
    }
}
