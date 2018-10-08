package mk.com.interworks.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import mk.com.interworks.data.db.LocalDB;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.CategoryEntity;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.repository.LocalDataRepository;

public class LocalDataSource implements LocalDataRepository{

    private LocalDB _db;

    public LocalDataSource(Context context) {


        RoomDatabase.Callback rdc = new RoomDatabase.Callback() {
            public void onCreate (SupportSQLiteDatabase db) {
                // do something after database has been created
                executeScript(context, R.raw.sql_default_categories, db);
            }
            public void onOpen (SupportSQLiteDatabase db) {
                // do something every time database is open
            }
        };

        _db = Room.databaseBuilder(context,
                LocalDB.class, Constants.APP_DB_NAME)
                .addCallback(rdc)
                .build();

    }

    private static void executeScript(Context context, int scriptFilename, SupportSQLiteDatabase myDB) {
        Log.i("executeScript" + scriptFilename, "Start");
        try {
            myDB.beginTransaction();
            loadFile(context, scriptFilename, myDB);
            myDB.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e("initialzeDatabase", "Error", e);
        } finally {
            myDB.endTransaction();
        }
        Log.i("executeScript" + scriptFilename, "End");
    }

    private static String loadFile(Context context, int dbDDLFile, SupportSQLiteDatabase myDB) {
        StringBuffer buffer = new StringBuffer();
        String expressions = "";

        try {
            InputStream is = context.getResources().openRawResource(dbDDLFile);
            if (is != null) {

                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.startsWith("--")) {
                        buffer.append(line);
                        if (line.endsWith(";")) {
                            Log.i("execute", line);
                            myDB.execSQL(buffer.toString().trim());
                            buffer = new StringBuffer();
                        } else {
                            buffer.append(' ');
                        }
                    }
                }
                reader.close();
            }

        } catch (Exception e) {
            Log.e("initialzeDatabase", "Error", e);
        }
        return expressions;

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
    public Single<VideoEntity> getVideoById(long id) {
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
    public Single<List<AnnotationEntity>> getAnnotationsForVideo(long videoId) {
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
    public Single<List<KeywordEntity>> getKeywordsForCategory(long categoryId) {
            return Single.create(e -> {
                try {
                    List<KeywordEntity> lsItems;
                    if(categoryId > -1){
                         lsItems = _db.keywordsDAO().getKeywordsForCategory(categoryId);
                    }else{
                        lsItems = _db.keywordsDAO().getDefaultKeywords();
                    }
                    e.onSuccess(lsItems);
                }catch (Exception e1){
                    e.onError(e1);
                }
            });
    }

    @Override
    public Single<List<FavoriteEntity>> getAllFavorites() {
        return Single.create(e -> {
            try {
                List<FavoriteEntity> lsItems = _db.favoritesDAO().getAllFavorites();
                e.onSuccess(lsItems);

            }catch (Exception e1){
                e.onError(e1);
            }
        });
    }

    @Override
    public Single<List<KeywordEntity>> getKeywordsForFavorite(long favoriteId) {
        return Single.create(e -> {
           try {
               List<KeywordEntity> lsItems;
               if(favoriteId > 0){
                   lsItems = _db.keywordsDAO().getKeywordsForFavorite(favoriteId);
               }else{
                   lsItems = _db.keywordsDAO().getDefaultKeywords();
               }
               e.onSuccess(lsItems);
           }catch (Exception e1){
               e.onError(e1);
           }
        });
    }


    @Override
    public Single<List<NoteEntity>> getNotesForAnnotation(long annotationId) {
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
