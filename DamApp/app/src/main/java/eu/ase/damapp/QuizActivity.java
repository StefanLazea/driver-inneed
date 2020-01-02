package eu.ase.damapp;

import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import eu.ase.damapp.fragment.QuestionsFragment;
import eu.ase.damapp.network.Item;

public class QuizActivity extends AppCompatActivity {

    private Item quizItem;
    private String currentCategory;
    private TextView tvQuestion;
    private RadioButton rbFirst;
    private RadioButton rbSecond;
    private RadioButton rbThrid;
    private RadioButton rbFourth;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initComponents();
        getExtras();
    }

    private void getExtras(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (extras.getString(MainActivity.START_QUIZ_KEY) != null) {
                String message = extras.getString(MainActivity.START_QUIZ_KEY);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
            if(extras.getString(QuestionsFragment.CURRENT_QUESTION) != null){
                quizItem = extras.getParcelable(QuestionsFragment.CURRENT_QUESTION);
                Toast.makeText(getApplicationContext(), "item ul " + quizItem, Toast.LENGTH_LONG).show();
            }
            if(extras.getString(QuestionsFragment.CURRENT_CATEGORY) != null){
                currentCategory = extras.getString(QuestionsFragment.CURRENT_CATEGORY);
            }
        }
    }

    private void initComponents(){
        tvQuestion = findViewById(R.id.quiz_tv_question);
        rbFirst = findViewById(R.id.quiz_rb_first);
        rbSecond = findViewById(R.id.quiz_rb_second);
        rbThrid = findViewById(R.id.quiz_rb_third);
        rbFourth = findViewById(R.id.quiz_rb_fourth);

    }

}
