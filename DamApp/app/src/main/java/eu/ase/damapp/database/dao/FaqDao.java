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

    @Query("SELECT sum(application_rating) FROM faq WHERE idUserFaq=:id")
    float selectSumAppRatingByUserId(long id);

    @Query("SELECT question_category FROM faq WHERE idUserFaq=:id and application_rating < :number")
    float selectCategoryNameForUserIdAndBelowNumber(long id, float number);
}
