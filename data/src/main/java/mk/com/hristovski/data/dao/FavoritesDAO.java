package mk.com.interworks.data.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mk.com.interworks.domain.Constants;
import mk.com.interworks.domain.model.FavoriteEntity;

@Dao
public interface FavoritesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity favorite);

    @Update
    void updateAnnotation(FavoriteEntity favorite);

    @Delete
    void delete(FavoriteEntity favorite);

    @Query("SELECT * FROM " + Constants.FAVORITE_TABLE_NAME)
    List<FavoriteEntity> getAllFavorites();
}
