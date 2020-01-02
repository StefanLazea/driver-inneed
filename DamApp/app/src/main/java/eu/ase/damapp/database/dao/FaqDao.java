package eu.ase.damapp.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import eu.ase.damapp.database.model.Faq;

@Dao
public interface FaqDao {
    @Query("SELECT * FROM Faq")
    List<Faq> getAll();

    @Insert
    long insert(Faq faq);
}
