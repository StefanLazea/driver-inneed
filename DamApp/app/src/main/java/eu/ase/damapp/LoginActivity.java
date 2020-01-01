package eu.ase.damapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import eu.ase.damapp.database.model.User;
import eu.ase.damapp.database.service.UserService;
import eu.ase.damapp.util.CustomSharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private final String pass = "test123123";
    private final String user = "stefan";
    public static final String CURRENT_USER = "currentUser";

    private EditText editTextUsername;
    private EditText editTextPass;
    private Button buttonLogin;
    private TextView tvWithoutAccount;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initComponents();
    }

    private boolean validate() {
        if (editTextUsername == null ||
                editTextUsername.getText().toString().trim().isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    getString(R.string.login_error_empty_username),
                    Toast.LENGTH_LONG).show();

            return false;
        }

        if (editTextPass == null ||
                editTextPass.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.login_empty_password),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean checkCredentials(User userFound) {
        if (!editTextUsername.getText().toString().equals(userFound.getUsername())) {
            Toast.makeText(getApplicationContext(),
                    R.string.login_invalid_username,
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (!editTextPass.getText().toString().equals(userFound.getPassword())) {
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
        tvWithoutAccount = findViewById(R.id.login_tv_without_account);
        tvRegister = findViewById(R.id.login_tv_register);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    getUserByUsernameFromDb(editTextUsername.getText().toString());
                }
            }

        });

        tvWithoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void getUserByUsernameFromDb(String username) {
        new UserService.GetOneByUsername(getApplication()) {
            @Override
            protected void onPostExecute(User result) {
                if (result != null) {
                    if (checkCredentials(result)) {
                        CustomSharedPreferences.setIdToPreferences(
                                getApplicationContext(),
                                RegisterActivity.SHARED_PREF_NAME,
                                result.getId());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(CURRENT_USER, result);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.login_user_not_found, Toast.LENGTH_LONG).show();
                }
            }
        }.execute(username);
    }
}
