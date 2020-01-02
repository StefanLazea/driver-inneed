package eu.ase.damapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import eu.ase.damapp.database.DatabaseManager;
import eu.ase.damapp.database.dao.UserCategoriesDao;
import eu.ase.damapp.database.model.UserCategories;

public class UserCategoriesService {
    private static UserCategoriesDao userCategoriesDao;

    public static class GetAll extends AsyncTask<Void, Void, List<UserCategories>> {
        public GetAll(Context context) {
            DatabaseManager.getInstance(context).getUserCategoryDao();
        }

        @Override
        protected List<UserCategories> doInBackground(Void... voids) {
            return userCategoriesDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<UserCategories, Void, UserCategories> {
        public Insert(Context context) {
            DatabaseManager.getInstance(context).getUserCategoryDao();
        }

        @Override
        protected UserCategories doInBackground(UserCategories... userCategories) {
            if (userCategories == null || userCategories.length != 1) {
                return null;
            }
            UserCategories uc = userCategories[0];
            long id = userCategoriesDao.insert(uc);
            if (id != -1) {
                uc.setUcId(id);
                return uc;
            }
            return null;
        }
    }
}
