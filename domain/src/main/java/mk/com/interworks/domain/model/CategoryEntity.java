package mk.com.interworks.domain.model;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import mk.com.interworks.domain.Constants;

@Entity(tableName = Constants.CATEGORY_TABLE_NAME)
public class CategoryEntity {

    public static final String NAME = "name";
    public static final String IMAGE_URI = "image_uri";
    public static final String ORIGIN = "origin";
    public static final String VERSION = "version";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private long id;
    @ColumnInfo(name = NAME)
    private String name;
    @ColumnInfo(name = IMAGE_URI)
    private String imageUri;
    @ColumnInfo(name = ORIGIN)
    private String origin;
    @ColumnInfo(name = VERSION)
    private int version;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
