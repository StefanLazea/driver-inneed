package eu.ase.damapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private String pass = "test123123";
    private String user = "stefan";

    EditText editTextUsername;
    EditText editTextPass;
    Button buttonLogin;

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private boolean validate() {
        if (editTextUsername == null
                && editTextUsername.getText().toString().trim().isEmpty()) {

            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.login_error_empty_username),
                    Toast.LENGTH_LONG).show();

            return false;
        }

        if (!editTextUsername.getText().toString().equals(user)) {
            Toast.makeText(getApplicationContext(),
                    R.string.login_invalid_username,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (editTextPass == null &&
                editTextPass.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.login_empty_password),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (!editTextPass.getText().toString().equals(pass)) {
            Toast.makeText(getApplicationContext(),
                    R.string.login_invalid_password,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void initComponents() {
        editTextUsername = findViewById(R.id.login_input_username);
        editTextPass = findViewById(R.id.login_input_password);
        buttonLogin = findViewById(R.id.login_button);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.login_message_success,
                            Toast.LENGTH_LONG).show();
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
