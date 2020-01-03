package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.database.model.Faq;
import eu.ase.damapp.database.service.FaqService;
import eu.ase.damapp.util.CustomSharedPreferences;
import eu.ase.damapp.util.FaqAdapter;

public class FaqActivity extends AppCompatActivity {
    public static final String FILE_NAME = "rapoarte.txt";
    private TextView tvRatingLabel;
    private TextView tvCategoryLabel;
    private TextView tvRating;
    private TextView tvCategory;
    private List<Faq> faqs = new ArrayList<>();
    private ListView lvFaq;
    private Button btnSave;
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
        saveInfo();
    }

    public void notifyInternal() {
        faqAdapter = (FaqAdapter) lvFaq.getAdapter();
        faqAdapter.notifyDataSetChanged();
    }

    private void initComponents() {
        tvCategoryLabel = findViewById(R.id.faq_tv_category_label);
        tvRatingLabel = findViewById(R.id.faq_tv_rating_label);
        tvRating = findViewById(R.id.faq_tv_rating);
        tvCategory = findViewById(R.id.faq_tv_category);
        lvFaq = findViewById(R.id.faq_lv);
        btnSave = findViewById(R.id.faq_btn_save);
        userId = CustomSharedPreferences.getIdFromPreferences(getApplicationContext(),
                RegisterActivity.SHARED_PREF_NAME);
        if (userId != -1) {
            populateListView();
        }
    }

    private void saveInfo() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
                    fos.write("Raportarea facuta pe baza intrebarilor frecvente\n".getBytes());
                    fos.write(tvRatingLabel.getText().toString().getBytes());
                    fos.write("\nAplicatia a primit rating-ul mediu la intrebarea cu categoria:\n".getBytes());
                    Toast.makeText(getApplicationContext(), "Saved to " + getFilesDir() + "/" + FILE_NAME,
                            Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
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
                    float averageRating = result / numerOfEntries;
                    tvRating.setText(String.valueOf(averageRating));
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
                    tvCategory.setText(result);
                }
            }
        }.execute(userId);
    }
}
