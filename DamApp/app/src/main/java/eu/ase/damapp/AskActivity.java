package eu.ase.damapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AskActivity extends AppCompatActivity {
    private EditText etQuestion;
    private Spinner spinner;
    private Button btnSend;
    //todo database storage of this questions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
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

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),
                        "Ati trimis cu succes intrebarea",
                        Toast.LENGTH_LONG
                ).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
