package eu.ase.damapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import eu.ase.damapp.fragment.QuestionsFragment;
import eu.ase.damapp.network.Item;

public class QuizActivity extends AppCompatActivity {
    private Item quizItem;
    private String currentCategory;
    private TextView tvQuestion;
    private TextView tvCategory;
    private RadioGroup rgAnswers;
    private RadioButton rbFirst;
    private RadioButton rbSecond;
    private RadioButton rbThrid;
    private RadioButton rbFourth;
    private Button next;
    private List<Item> currentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getExtras();
        initComponents();
    }

    private void getExtras() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString(QuestionsFragment.CURRENT_CATEGORY) != null) {
                currentCategory = extras.getString(QuestionsFragment.CURRENT_CATEGORY);
            }
            if (extras.getString("StartKey") != null) {
                String message = extras.getString("StartKey");
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            } else {
                quizItem = extras.getParcelable(QuestionsFragment.CURRENT_QUESTION);
                Toast.makeText(getApplicationContext(), quizItem.getQuestion(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initComponents() {
        rgAnswers = findViewById(R.id.quiz_rg_answers);
        tvQuestion = findViewById(R.id.quiz_tv_question);
        tvCategory = findViewById(R.id.quiz_tv_category);
        rbFirst = findViewById(R.id.quiz_rb_first);
        rbSecond = findViewById(R.id.quiz_rb_second);
        rbThrid = findViewById(R.id.quiz_rb_third);
        rbFourth = findViewById(R.id.quiz_rb_fourth);
        next = findViewById(R.id.quiz_btn_next);

        tvQuestion.setText(quizItem.getQuestion());
        String strCategory = getString(R.string.quiz_category) + currentCategory;
        tvCategory.setText(strCategory);

        rbFirst.setText(quizItem.getAnswer().getFirstAnswer());
        rbSecond.setText(quizItem.getAnswer().getSecondAnswer());
        rbThrid.setText(quizItem.getAnswer().getThirdAnswer());
        rbFourth.setText(quizItem.getAnswer().getFourthAnswer());


        rgAnswers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    next.setBackgroundColor(Color.GREEN);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RadioButton rb = findViewById(rgAnswers.getCheckedRadioButtonId());
                if (rb != null) {
                    String answer = rb.getText().toString();
                    if (answer.equals(quizItem.getAnswer().getCorrect())) {
                        Toast.makeText(getApplicationContext(),
                                R.string.quiz_correct_answer, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(),
                                R.string.quiz_incorrect,
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.quiz_no_answer_selected, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
