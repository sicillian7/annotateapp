package mk.com.interworks.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import mk.com.interworks.domain.Constants;
import mk.com.interworks.domain.model.BaseEntity;
import mk.com.interworks.domain.model.KeywordEntity;

@Dao
public interface KeywordsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(KeywordEntity kw);

    @Update
    void updateKeyword(KeywordEntity kw);

    @Delete
    void delete(KeywordEntity kw);

    @Query("SELECT * FROM " + Constants.KEYWORD_TABLE_NAME + " WHERE " + KeywordEntity.CATEGORY_ID + "=:categoryId")
    List<KeywordEntity> getKeywordsForCategory(long categoryId);

    @Query("SELECT * FROM " + Constants.KEYWORD_TABLE_NAME + " WHERE " + KeywordEntity.CATEGORY_ID + " =1")
    List<KeywordEntity> getDefaultKeywords();
}
