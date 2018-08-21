package mk.com.interworks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import mk.com.interworks.domain.Constants;

@Entity(tableName = Constants.VIDEO_TABLE_NAME)
public class VideoEntity{

    public static final String ACTION = "action";
    public static final String CONTENT_TYPE = "content_type";
    public static final String DATE_CREATED = "date_created";
    public static final String DATE_UPLOADED = "date_uploaded";
    public static final String DISPLAY_ORDER = "display_order";
    public static final String FOLDER = "folder";
    public static final String IMPORTED = "imported";
    public static final String IS_UPLOADED_TO_SERVER = "is_uploaded_to_server";
    public static final String NAME = "name";
    public static final String NEW_VIDEO = "new_video";
    public static final String ORIGIN = "origin";
    public static final String PATH = "path";
    public static final String THUMB = "thumbnail";
    public static final String DESCRIPTION = "description";
    public static final String DURATION = "duration";
    public static final String CREATED_BY = "created_by";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";



    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private long id;
    @ColumnInfo(name = ACTION)
    private int action;
    @ColumnInfo(name = CONTENT_TYPE)
    private String contentType;
    @ColumnInfo(name = DATE_CREATED)
    private long dateCreated;
    @ColumnInfo(name = DATE_UPLOADED)
    private long dateUploaded;
    @ColumnInfo(name = DISPLAY_ORDER)
    private int displayOrder;
    @ColumnInfo(name = FOLDER)
    private boolean folder;
    @ColumnInfo(name = IMPORTED)
    private boolean imported;
    @ColumnInfo(name = IS_UPLOADED_TO_SERVER)
    private int isUploadedToServer;
    @ColumnInfo(name = NAME)
    private String name;
    @ColumnInfo(name = NEW_VIDEO)
    private boolean newVideo;
    @ColumnInfo(name = ORIGIN)
    private String origin;
    @ColumnInfo(name = PATH)
    private String path;
    @ColumnInfo(name = DESCRIPTION)
    private String description;
    @ColumnInfo(name = DURATION)
    private long duration;
    @ColumnInfo(name = CREATED_BY)
    private String createdBy;
    @ColumnInfo(name = LATITUDE)
    private double lat;
    @ColumnInfo(name = LONGITUDE)
    private double lon;


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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getDateUploaded() {
        return dateUploaded;
    }

    public void setDateUploaded(long dateUploaded) {
        this.dateUploaded = dateUploaded;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public int getIsUploadedToServer() {
        return isUploadedToServer;
    }

    public void setIsUploadedToServer(int isUploadedToServer) {
        this.isUploadedToServer = isUploadedToServer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNewVideo() {
        return newVideo;
    }

    public void setNewVideo(boolean newVideo) {
        this.newVideo = newVideo;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
