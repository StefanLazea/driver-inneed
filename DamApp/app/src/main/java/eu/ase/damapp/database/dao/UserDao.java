package eu.ase.damapp.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import eu.ase.damapp.database.model.User;

//nu face conexiune
@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE id = :id")
    User findUserById(long id);

    @Query("SELECT * FROM users WHERE username = :username")
    User findUserByUsername(String username);

    @Insert
    long insert(User user);

    @Update
    int update(User user);

}
