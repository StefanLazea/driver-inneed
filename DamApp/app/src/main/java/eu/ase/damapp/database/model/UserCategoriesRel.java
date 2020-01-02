package eu.ase.damapp.database.model;

import androidx.room.Entity;

@Entity(primaryKeys = {"userId", "categoryId"})
public class UserCategoriesRel {
    public long userId;
    public long categoryId;
}
