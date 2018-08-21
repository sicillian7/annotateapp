package mk.com.interworks.domain.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public abstract class BaseEntity {

    public static final String ID = "id";

    protected static final long serialVersionUID = 1L;

    @NonNull
    @PrimaryKey
    private long id = -1;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setId(long id){
        this.id = (int) id;
    }
}
