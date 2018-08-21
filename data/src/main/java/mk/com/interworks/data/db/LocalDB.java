package mk.com.interworks.data.db;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import mk.com.interworks.data.dao.AnnotationDAO;
import mk.com.interworks.data.dao.VideoDAO;
import mk.com.interworks.domain.model.AnnotationEntity;
import mk.com.interworks.domain.model.CategoryEntity;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.model.KeywordEntity;
import mk.com.interworks.domain.model.LocationEntity;
import mk.com.interworks.domain.model.NoteEntity;
import mk.com.interworks.domain.model.VideoEntity;
import mk.com.interworks.domain.model.relationships.FavoriteKeywordJoin;

@Database(entities = {AnnotationEntity.class, CategoryEntity.class, FavoriteEntity.class,
        KeywordEntity.class, LocationEntity.class, NoteEntity.class, VideoEntity.class, FavoriteKeywordJoin.class}, version = 1, exportSchema = false)
public abstract class LocalDB extends RoomDatabase {

    public abstract VideoDAO videoDAO();
    public abstract AnnotationDAO annotationDAO();

}
