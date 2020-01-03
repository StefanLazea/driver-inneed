package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.database.model.Faq;
import eu.ase.damapp.database.service.FaqService;
import eu.ase.damapp.util.CustomSharedPreferences;
import eu.ase.damapp.util.FaqAdapter;

public class FaqActivity extends AppCompatActivity {
    private TextView tvRatingLabel;
    private TextView tvCategoryLabel;
    private List<Faq> faqs = new ArrayList<>();
    private ListView lvFaq;
    private long userId;
    private FaqAdapter faqAdapter;
    private float numerOfEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        initComponents();
        getNumberOfEntries();
        getSumForAppRating();
        getCategory();
    }

    public void notifyInternal() {
        faqAdapter = (FaqAdapter) lvFaq.getAdapter();
        faqAdapter.notifyDataSetChanged();
    }

    private void initComponents() {
        tvCategoryLabel = findViewById(R.id.faq_tv_category_label);
        tvRatingLabel = findViewById(R.id.faq_tv_rating_label);
        lvFaq = findViewById(R.id.faq_lv);
        userId = CustomSharedPreferences.getIdFromPreferences(getApplicationContext(),
                RegisterActivity.SHARED_PREF_NAME);
        if (userId != -1) {
            populateListView();
        }
    }

    private void populateListView() {
        if (getApplicationContext() != null) {
            faqAdapter = new FaqAdapter(getApplicationContext(),
                    R.layout.lv_row_faq,
                    faqs,
                    getLayoutInflater());
            lvFaq.setAdapter(faqAdapter);
            getFaqsFromDb();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getFaqsFromDb() {
        new FaqService.GetAll(getApplicationContext()) {
            @Override
            protected void onPostExecute(List<Faq> results) {
                if (results != null) {
                    faqs.clear();
                    faqs.addAll(results);
                    notifyInternal();
                }
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void getSumForAppRating() {
        new FaqService.GetSum(getApplicationContext()) {
            @Override
            protected void onPostExecute(Float result) {
                if (result > 0) {
                    String curr = tvRatingLabel.getText().toString();
                    float averageRating = result / numerOfEntries;
                    String finalValue = curr + String.valueOf(averageRating);
                    tvRatingLabel.setText(finalValue);
                }
            }
        }.execute(userId);
    }

    @SuppressLint("StaticFieldLeak")
    private void getNumberOfEntries() {
        new FaqService.GetNumberOfEntries(getApplicationContext()) {
            @Override
            protected void onPostExecute(Integer result) {
                numerOfEntries = result;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void getCategory() {
        new FaqService.GetCategoryNameGivenUserIdAndRating(getApplicationContext()) {
            @Override
            protected void onPostExecute(String result) {
                if (result != null) {
                    String displayValue = tvCategoryLabel.getText().toString().concat(result);
                    tvCategoryLabel.setText(displayValue);
                }
            }
        }.execute(userId);
    }
}
