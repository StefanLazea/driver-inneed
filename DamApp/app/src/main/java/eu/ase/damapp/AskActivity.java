package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private Faq faq;
    private long userId;

    //todo database storage of this questions
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

        tvRatingApp = findViewById(R.id.ask_tv_ratingapp);

        tvRatingApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker picker = new NumberPicker(AskActivity.this);
                picker.setMinValue(HomeFragment.MIN_RATING_PICKER);
                picker.setMaxValue(HomeFragment.MIN_RATING_PICKER);
                ratingPicker(picker);
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

                    faq = new Faq(etQuestion.getText().toString(), category, 5, userId);
                    insertCategoryIntoDB(faq);
                    Intent intent = new Intent(getApplicationContext(), FaqActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void ratingPicker(final NumberPicker picker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AskActivity.this);
        builder
                .setTitle(getString(R.string.home_ratingPicker_title))
                .setMessage(getString(R.string.home_ratingPicker_message))
                .setPositiveButton(getString(R.string.home_button_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setRating(picker.getValue());
                    }
                })
                .setNegativeButton(getString(R.string.home_cancel_button),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        builder.setView(picker);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setRating(float number) {

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
