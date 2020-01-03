package eu.ase.damapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.damapp.database.model.Category;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Insert
    long insert(Category category);

    @Update
    int update(Category category);

    @Query("SELECT * FROM  categories WHERE name=:categoryName")
    Category getCategoryByName(String categoryName);
}
