package eu.ase.damapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import eu.ase.damapp.database.DatabaseManager;
import eu.ase.damapp.database.dao.UserDao;
import eu.ase.damapp.database.model.User;

public class UserService {
    private static UserDao userDao;

    public static class GetOneByUsername extends AsyncTask<String, Void, User> {
        public GetOneByUsername(Context context) {
            userDao = DatabaseManager
                    .getInstance(context)
                    .getUserDao();
        }

        @Override
        protected User doInBackground(String... strings) {
            if (strings != null && strings.length != 1) {
                return null;
            }

            String username = strings[0];

            User user = userDao.findUserByUsername(username);

            if (user != null) {
                return user;
            }
            return null;
        }
    }

    public static class GetOneById extends AsyncTask<Long, Void, User> {
        public GetOneById(Context context) {
            userDao = DatabaseManager.getInstance(context).getUserDao();
        }

        @Override
        protected User doInBackground(Long... longs) {
            if (longs != null && longs.length != 1) {
                return null;
            }

            long id = longs[0];

            User user = userDao.findUserById(id);
            if (user != null) {
                return user;
            }
            return null;
        }
    }

    public static class Insert extends AsyncTask<User, Void, User> {

        public Insert(Context context) {
            userDao = DatabaseManager
                    .getInstance(context)
                    .getUserDao();
        }

        @Override
        protected User doInBackground(User... users) {
            if (users != null && users.length != 1) {
                return null;
            }

            User user = users[0];
            long id = userDao.insert(user);

            if (id != -1) {
                user.setId(id);
                return user;
            }
            return null;
        }
    }

    public static class Delete extends
            AsyncTask<User, Void, Integer> {
        public Delete(Context context) {
            userDao = DatabaseManager
                    .getInstance(context)
                    .getUserDao();
        }

        @Override
        protected Integer doInBackground(User... users) {
            if (users == null || users.length != 1) {
                return -1;
            }
            return userDao.delete(users[0]);
        }
    }
}
