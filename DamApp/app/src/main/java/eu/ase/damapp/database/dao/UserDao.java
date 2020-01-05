package eu.ase.damapp.database.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import eu.ase.damapp.database.model.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    User findUserById(long id);

    @Query("SELECT * FROM users WHERE username = :username")
    User findUserByUsername(String username);

    @Insert
    long insert(User user);

    @Delete
    int delete(User user);

}
