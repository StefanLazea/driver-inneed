package eu.ase.damapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import eu.ase.damapp.database.DatabaseManager;
import eu.ase.damapp.database.dao.CategoryDao;
import eu.ase.damapp.database.model.Category;

public class CategoryService {
    private static CategoryDao categoryDao;

    public static class GetAll extends AsyncTask<Void, Void, List<Category>> {

        public GetAll(Context context) {
            categoryDao = DatabaseManager
                    .getInstance(context)
                    .getCategoryDao();
        }

        @Override
        protected List<Category> doInBackground(Void... voids) {
            return categoryDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<Category, Void, Category> {
        public Insert(Context context) {
            categoryDao = DatabaseManager.getInstance(context).getCategoryDao();
        }

        @Override
        protected Category doInBackground(Category... categories) {
            if (categories == null || categories.length != 1) {
                return null;
            }
            Category category = categories[0];
            long id = categoryDao.insert(category);
            if (id != -1) {
                category.setId(id);
                return category;
            }
            return null;
        }
    }

    public static class Update extends AsyncTask<Category, Void, Integer> {
        public Update(Context context) {
            categoryDao = DatabaseManager
                    .getInstance(context)
                    .getCategoryDao();
        }

        @Override
        protected Integer doInBackground(Category... categories) {
            if (categories == null || categories.length != 1) {
                return -1;
            }
            return categoryDao.update(categories[0]);
        }
    }
}
