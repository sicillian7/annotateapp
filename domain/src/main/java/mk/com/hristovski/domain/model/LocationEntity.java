package mk.com.interworks.domain.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import mk.com.interworks.domain.Constants;

@Entity
public class LocationEntity{
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String RADIUS = "radius";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = Constants.ID)
    private long id;
    @ColumnInfo(name = LATITUDE)
    private double latitude;
    @ColumnInfo(name = LONGITUDE)
    private double longitude;
    @ColumnInfo(name = RADIUS)
    private double radius;

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
