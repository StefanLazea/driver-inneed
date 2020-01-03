package eu.ase.damapp.database.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import eu.ase.damapp.database.DatabaseManager;
import eu.ase.damapp.database.dao.FaqDao;
import eu.ase.damapp.database.model.Faq;

public class FaqService {
    private static FaqDao faqDao;

    public static class GetAll extends AsyncTask<Void, Void, List<Faq>> {
        public GetAll(Context context) {
            faqDao = DatabaseManager.getInstance(context).getFaqDao();
        }

        @Override
        protected List<Faq> doInBackground(Void... voids) {
            return faqDao.getAll();
        }
    }

    public static class Insert extends AsyncTask<Faq, Void, Faq> {
        public Insert(Context context) {
            faqDao = DatabaseManager.getInstance(context).getFaqDao();
        }

        @Override
        protected Faq doInBackground(Faq... faqs) {
            if (faqs == null || faqs.length != 1) {
                return null;
            }
            Faq uc = faqs[0];
            long id = faqDao.insert(uc);
            if (id != -1) {
                uc.setId(id);
                return uc;
            }
            return null;
        }
    }

    public static class GetSum extends AsyncTask<Long, Void, Float> {
        public GetSum(Context context) {
            faqDao = DatabaseManager.getInstance(context).getFaqDao();
        }

        @Override
        protected Float doInBackground(Long... results) {
            if (results != null && results.length != 1) {
                return null;
            }
            float sum = results[0];
            if (sum > 0) {
                return sum;
            }
            return null;
        }
    }

    public static class GetNumberOfEntries extends AsyncTask<Void, Void, Integer>{
        public GetNumberOfEntries(Context context) {
            faqDao = DatabaseManager.getInstance(context).getFaqDao();
        }

        @Override
        protected Integer doInBackground(Void... results) {
           return faqDao.countEntries();
        }
    }
}
