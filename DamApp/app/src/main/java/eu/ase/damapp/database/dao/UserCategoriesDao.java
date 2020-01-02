package eu.ase.damapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import eu.ase.damapp.database.model.UserCategories;

@Dao
public interface UserCategoriesDao {
    @Query("SELECT * FROM users_categories")
    List<UserCategories> getAll();

    @Insert
    long insert(UserCategories userCategories);
}
