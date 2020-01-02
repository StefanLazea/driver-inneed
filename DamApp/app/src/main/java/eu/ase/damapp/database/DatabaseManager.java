package eu.ase.damapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import eu.ase.damapp.database.dao.CategoryDao;
import eu.ase.damapp.database.dao.FaqDao;
import eu.ase.damapp.database.dao.UserDao;
import eu.ase.damapp.database.model.Category;
import eu.ase.damapp.database.model.Faq;
import eu.ase.damapp.database.model.User;

@Database(entities = {User.class, Category.class, Faq.class}, exportSchema = false, version = 1)
public abstract class DatabaseManager extends RoomDatabase {
    private static final String DB_NAME = "driving_school";
    private static DatabaseManager dbManager;

    public static DatabaseManager getInstance(Context context) {
        if (dbManager == null) {
            synchronized (DatabaseManager.class) {
                if (dbManager == null) {
                    dbManager = Room
                            .databaseBuilder(context, DatabaseManager.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
            return dbManager;
        }
        return dbManager;
    }

    public abstract UserDao getUserDao();
    public abstract CategoryDao getCategoryDao();
    public abstract FaqDao getFaqDao();
}
