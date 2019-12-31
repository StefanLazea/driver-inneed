package eu.ase.damapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eu.ase.damapp.database.model.User;
import eu.ase.damapp.database.service.UserService;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegister;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initComponents();
    }

    private void initComponents() {

        etUsername = findViewById(R.id.register_username);
        etPassword = findViewById(R.id.register_tv_password);
        etConfirmPassword = findViewById(R.id.register_tv_confirm);


        btnRegister = findViewById(R.id.register_btn);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validate() && checkPassword()) {
                    User user = new User(etUsername.getText().toString(), etPassword.getText().toString());
                    insertUserIntoDb(user);
                }
            }
        });

    }

    private boolean validate() {
        if (etUsername.getText().toString().isEmpty() || etUsername == null) {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.login_error_empty_username),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (etPassword.getText().toString().isEmpty() || etPassword == null) {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.login_empty_password),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (etConfirmPassword.getText().toString().isEmpty() || etConfirmPassword == null) {
            Toast.makeText(getApplicationContext(),
                    R.string.register_confirm_message_empty,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

    private boolean checkPassword() {

        if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(),
                    R.string.register_pass_mismatch,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void insertUserIntoDb(User user) {
        new UserService.Insert(getApplicationContext()) {
            @Override
            protected void onPostExecute(User result) {
                if (result != null) {
                    Toast.makeText(getApplicationContext(),
                            "Cont creat " + result.getId(),
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        }.execute(user);
    }
}
