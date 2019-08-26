package mk.com.interworks.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import mk.com.interworks.domain.Constants;
import mk.com.interworks.domain.model.BaseEntity;
import mk.com.interworks.domain.model.VideoEntity;

@Dao
public interface VideoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(VideoEntity video);

    @Delete
    void delete(VideoEntity video);

    @Query("SELECT * FROM " + Constants.VIDEO_TABLE_NAME)
    List<VideoEntity> getAllVideos();

    @Query("SELECT * FROM " + Constants.VIDEO_TABLE_NAME + " WHERE " + BaseEntity.ID + "=:videoId")
    VideoEntity getVideoById(long videoId);
}
