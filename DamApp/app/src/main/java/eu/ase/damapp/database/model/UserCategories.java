package eu.ase.damapp.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

@Entity(
        tableName = "users_categories",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id"),
                @ForeignKey(
                        entity = Category.class,
                        parentColumns = "id",
                        childColumns = "category_id"
                )
        },
        indices = {@Index("user_id"), @Index("category_id")}

)
public class UserCategories {
    @PrimaryKey
    @ColumnInfo(name = "ucId")
    public long ucId;
    @ColumnInfo(name = "user_id")
    public long userId;
    @ColumnInfo(name = "category_id")
    public long categoryId;

    public long getUcId() {
        return ucId;
    }

    public void setUcId(long ucId) {
        this.ucId = ucId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}


