package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import eu.ase.damapp.database.model.Faq;
import eu.ase.damapp.database.service.FaqService;
import eu.ase.damapp.fragment.HomeFragment;
import eu.ase.damapp.util.CustomSharedPreferences;

public class AskActivity extends AppCompatActivity {
    private EditText etQuestion;
    private Spinner spinner;
    private Button btnSend;
    private TextView tvRatingApp;
    private NumberPicker numberPicker;
    private Faq faq;
    private long userId;
    private float selectedRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        userId = CustomSharedPreferences.getIdFromPreferences(getApplicationContext(), RegisterActivity.SHARED_PREF_NAME);
        initComponents();
    }

    private void initComponents() {
        etQuestion = findViewById(R.id.ask_et_category);
        spinner = findViewById(R.id.ask_spinner_categories);
        btnSend = findViewById(R.id.ask_button_send);
        ArrayAdapter<CharSequence> categoriesAdapter =
                ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.quiz_questions_categories,
                        R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(categoriesAdapter);
        numberPicker = (NumberPicker)findViewById(R.id.ask_np_rating);
        tvRatingApp = findViewById(R.id.ask_tv_ratingapp);

        numberPicker.setMinValue(HomeFragment.MIN_RATING_PICKER);
        numberPicker.setMaxValue(HomeFragment.MAX_RATING_PICKER);

        numberPicker.setWrapSelectorWheel(true);

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                selectedRating =(float) newVal;
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.ask_success_message),
                            Toast.LENGTH_LONG
                    ).show();

                    String category = spinner.getSelectedItem().toString();

                    faq = new Faq(etQuestion.getText().toString(), category, selectedRating, userId);
                    Toast.makeText(getApplicationContext(), String.valueOf(selectedRating
                            ),
                            Toast.LENGTH_LONG).show();
                    insertCategoryIntoDB(faq);
                    Intent intent = new Intent(getApplicationContext(), FaqActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean validate() {
        if (etQuestion.getText().toString().trim().isEmpty() || etQuestion == null) {
            Toast.makeText(getApplicationContext(), R.string.ask_etQuestion_error,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void insertCategoryIntoDB(Faq faq) {
        new FaqService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(Faq result) {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),
                            R.string.ask_new_question_message,
                            Toast.LENGTH_LONG).show();
                }
            }
        }.execute(faq);
    }

}
