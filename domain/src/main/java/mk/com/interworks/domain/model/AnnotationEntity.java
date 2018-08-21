package mk.com.interworks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import mk.com.interworks.domain.Constants;

@Entity(tableName = Constants.ANNOTATION_TABLE_NAME,
        indices = {@Index(value = {AnnotationEntity.VIDEO_ID})},
        foreignKeys = @ForeignKey(entity = VideoEntity.class, parentColumns = Constants.ID, childColumns = AnnotationEntity.VIDEO_ID))
public class AnnotationEntity{

    public static final String VIDEO_ID = "video_id";
    public static final String CATEGORY_ID = "category_id";
    public static final String DURATION = "duration";
    public static final String ACTION = "action";
    public static final String DESC = "description";
    public static final String KEYWORD_ID = "keyword_id";
    public static final String NAME = "name";
    public static final String SERVER_ID = "server_id";
    public static final String START = "start";
    public static final String X = "x";
    public static final String Y = "y";

    public enum State {
        add, delete, update, synced
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private long id;
    @ColumnInfo(name = ACTION)
    private int action;
    @ColumnInfo(name = VIDEO_ID)
    private int videoId;
    @ColumnInfo(name = CATEGORY_ID)
    private int categoryId;
    @ColumnInfo(name = DURATION)
    private float duration;
    @ColumnInfo(name = X)
    private float x;
    @ColumnInfo(name = Y)
    private float y;
    @ColumnInfo(name = START)
    private float from;
    @ColumnInfo(name = KEYWORD_ID)
    private int keywordId;
    @ColumnInfo(name = NAME)
    private String name;
    @ColumnInfo(name = DESC)
    private String description;
    @ColumnInfo(name = SERVER_ID)
    private String serverId;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getFrom() {
        return from;
    }

    public void setFrom(float from) {
        this.from = from;
    }

    public int getKeywordId() {
        return keywordId;
    }

    public void setKeywordId(int keywordId) {
        this.keywordId = keywordId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
