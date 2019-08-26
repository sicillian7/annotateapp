package mk.com.interworks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import mk.com.interworks.domain.Constants;

@Entity(tableName = Constants.NOTE_TABLE_NAME,
        indices = {@Index(value = { NoteEntity.ANNNOTATION_ID})},
        foreignKeys = @ForeignKey(entity = AnnotationEntity.class, parentColumns = BaseEntity.ID, childColumns = NoteEntity.ANNNOTATION_ID))
public class NoteEntity{

    public static final String ANNNOTATION_ID = "annotation_id";
    public static final String ACTION = "action";
    public static final String AUDIO_URL = "audio_url";
    public static final String COMMENTED_BY = "commented_by";
    public static final String CONTENT = "content";
    public static final String DATE_CREATED = "date_created";
    public static final String DISPLAY_ORDER = "display_order";
    public static final String DURATION = "duration";
    public static final String ELAPSED_TIME = "elapsed_time";
    public static final String TYPE = "type";
    public static final String VIDEO_URI = "video_uri";
    public static final String VERSION= "version";
    public static final String USER_ID = "user_id";
    public static final String SERVER_ID = "server_id";

    private static final long serialVersionUID = 1L;

    public enum ContentType{
        TEXT, AUDIO, VIDEO
    }

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private long id;
    @ColumnInfo(name = ANNNOTATION_ID)
    private int annotationID;
    @ColumnInfo(name = ACTION)
    private int action;
    @ColumnInfo(name = AUDIO_URL)
    private String audioUrl;
    @ColumnInfo(name = COMMENTED_BY)
    private String commentedBy;
    @ColumnInfo(name = CONTENT)
    String content;
    @ColumnInfo(name = DISPLAY_ORDER)
    private int displayOrder;
    @ColumnInfo(name = DURATION)
    private long duration;
    @ColumnInfo(name = ELAPSED_TIME)
    private long elapsedTime;
    @ColumnInfo(name = TYPE)
    private int type;
    @ColumnInfo(name = VIDEO_URI)
    private String videoUri;

    @ColumnInfo(name = DATE_CREATED)
    private long dateCreated;

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

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setContentType(ContentType t){
        switch (t){
            case TEXT:
                type = 0;
                break;
            case AUDIO:
                type = 1;
                break;
            case VIDEO:
                type = 2;
        }
    }

    public ContentType getContentType(){
        switch (type){
            case 0:
                return ContentType.TEXT;
            case 1:
                return ContentType.AUDIO;
            case 2:
                return ContentType.VIDEO;
                default:
                    return ContentType.TEXT;
        }
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }


    public int getAnnotationID() {
        return annotationID;
    }

    public void setAnnotationID(int annotationID) {
        this.annotationID = annotationID;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }
}
