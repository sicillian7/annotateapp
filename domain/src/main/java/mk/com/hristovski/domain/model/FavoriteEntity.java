package mk.com.interworks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import mk.com.interworks.domain.Constants;

@Entity(tableName = Constants.FAVORITE_TABLE_NAME)
public class FavoriteEntity{

    public static final String DISPLAY_KEYWORD_ORDERS = "display_keyword_orders";
    public static final String DISPLAY_ORDER = "display_order";
    public static final String NAME = "name";
    public static final String ORIGIN = "origin";
    public static final String SELECTED = "selected";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private long id;
    @ColumnInfo(name = DISPLAY_KEYWORD_ORDERS)
    private String displayKeywordOrders;
    @ColumnInfo(name = DISPLAY_ORDER)
    private int displayOrder;
    @ColumnInfo(name = NAME)
    private String name;
    @ColumnInfo(name = ORIGIN)
    private String origin;
    @ColumnInfo(name = SELECTED)
    private boolean selected;

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

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getDisplayKeywordOrders() {
        return displayKeywordOrders;
    }

    public void setDisplayKeywordOrders(String displayKeywordOrders) {
        this.displayKeywordOrders = displayKeywordOrders;
    }
}
