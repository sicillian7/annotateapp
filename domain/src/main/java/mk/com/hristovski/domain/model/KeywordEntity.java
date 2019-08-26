package mk.com.interworks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import mk.com.interworks.domain.Constants;

@Entity(tableName = Constants.KEYWORD_TABLE_NAME)
public class KeywordEntity{

    public static final String BOOKMARKED = "bookmarked";
    public static final String CATEGORY_ID = "categoryId";
    public static final String DISPLAY_ORDER = "display_order";
    public static final String IMAGE_URI = "image_uri";
    public static final String NAME = "name";
    public static final String OLD = "old";
    public static final String RES_PATH = "resPath";
    public static final String VERSION = "version";


    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ID)
    private long id;
    @Ignore
    @ColumnInfo(name = BOOKMARKED)
    private boolean bookmarked;
    @ColumnInfo(name = CATEGORY_ID)
    private long categoryId;
    @Ignore
    @ColumnInfo(name = DISPLAY_ORDER)
    private int displayOrder;
//    @ColumnInfo(name = IMAGE_URI)
//    private String imageUri;
    @ColumnInfo(name = NAME)
    private String name;
    @Ignore
    @ColumnInfo(name = OLD)
    private long old;
    @ColumnInfo(name = RES_PATH)
    private String resPath;
    @Ignore
    @ColumnInfo(name = VERSION)
    private long version;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public long getOld() {
        return old;
    }

    public void setOld(long old) {
        this.old = old;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getImageUri() {
//        return imageUri;
//    }
//
//    public void setImageUri(String imageUri) {
//        this.imageUri = imageUri;
//    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public String getResPath() {
        return resPath;
    }

    public void setResPath(String resPath) {
        this.resPath = resPath;
    }
}
