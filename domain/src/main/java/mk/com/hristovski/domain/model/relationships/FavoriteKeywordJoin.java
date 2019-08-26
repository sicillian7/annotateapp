package mk.com.interworks.domain.model.relationships;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import mk.com.interworks.domain.Constants;
import mk.com.interworks.domain.model.BaseEntity;
import mk.com.interworks.domain.model.FavoriteEntity;
import mk.com.interworks.domain.model.KeywordEntity;

@Entity(tableName = Constants.FAVORITE_KEYWORD_JOIN_TABLE_NAME, primaryKeys = { FavoriteKeywordJoin.FAVORITE_ID, FavoriteKeywordJoin.KEYWORD_ID },
        indices = {@Index(value = {FavoriteKeywordJoin.KEYWORD_ID})},
        foreignKeys = {
                @ForeignKey(entity = FavoriteEntity.class,
                        parentColumns = BaseEntity.ID,
                        childColumns = FavoriteKeywordJoin.FAVORITE_ID,
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = KeywordEntity.class,
                        parentColumns = BaseEntity.ID,
                        childColumns = FavoriteKeywordJoin.KEYWORD_ID)
        })
public class FavoriteKeywordJoin {

    public static final String FAVORITE_ID = "favorite_id";
    public static final String KEYWORD_ID = "keyword_id";

    @NonNull
    @ColumnInfo(name = FAVORITE_ID)
    private String favoriteId;
    @NonNull
    @ColumnInfo(name = KEYWORD_ID)
    private String keywordId;

    public FavoriteKeywordJoin(String favoriteId, String keywordId) {
        this.favoriteId = favoriteId;
        this.keywordId = keywordId;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(String keywordId) {
        this.keywordId = keywordId;
    }
}
