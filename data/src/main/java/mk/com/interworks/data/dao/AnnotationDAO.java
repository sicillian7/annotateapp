package mk.com.interworks.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mk.com.interworks.domain.Constants;
import mk.com.interworks.domain.model.AnnotationEntity;

@Dao
public interface AnnotationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(AnnotationEntity annotation);

    @Update
    void updateAnnotation(AnnotationEntity annotation);

    @Delete
    void delete(AnnotationEntity annotation);

    @Query("SELECT * FROM " + Constants.ANNOTATION_TABLE_NAME + " WHERE " + AnnotationEntity.VIDEO_ID + "=:videoId")
    List<AnnotationEntity> getAnnotationsForVideo(long videoId);

    @Query("DELETE FROM " + Constants.ANNOTATION_TABLE_NAME + " WHERE " + AnnotationEntity.VIDEO_ID + "=:videoId")
    void deleteAllAnnotationsForVideo(long videoId);
}
