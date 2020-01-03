package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.database.model.Faq;
import eu.ase.damapp.database.service.FaqService;
import eu.ase.damapp.util.CustomSharedPreferences;
import eu.ase.damapp.util.FaqAdapter;

public class FaqActivity extends AppCompatActivity {
    private TextView tvQuestionLabel;
    private TextView tvCategoryLabel;
    private List<Faq> faqs = new ArrayList<>();
    private ListView lvFaq;
    private long userId;
    private FaqAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        initComponents();

    }

    public void notifyInternal() {
        faqAdapter = (FaqAdapter) lvFaq.getAdapter();
        faqAdapter.notifyDataSetChanged();
    }

    private void initComponents() {
        tvCategoryLabel = findViewById(R.id.faq_tv_category_label);
        tvQuestionLabel = findViewById(R.id.faq_tv_rating_label);
        lvFaq = findViewById(R.id.faq_lv);
        userId = CustomSharedPreferences.getIdFromPreferences(getApplicationContext(), RegisterActivity.SHARED_PREF_NAME);
        if (userId != -1) {
            populateListView();
        }
    }

    private void populateListView() {
        if (getApplicationContext() != null) {
            faqAdapter = new FaqAdapter(getApplicationContext(),
                    R.layout.lv_row_question,
                    faqs,
                    getLayoutInflater());
            lvFaq.setAdapter(faqAdapter);
            getFaqsFromDb();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void getFaqsFromDb(){
        new FaqService.GetAll(getApplicationContext()){
            @Override
            protected void onPostExecute(List<Faq> results) {
                if(results!=null){
                    faqs.clear();
                    faqs.addAll(results);
                    notifyInternal();
                }
            }
        }.execute();
    }
}
