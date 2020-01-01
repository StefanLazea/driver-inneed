package eu.ase.damapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eu.ase.damapp.database.model.User;
import eu.ase.damapp.database.service.UserService;
import eu.ase.damapp.util.CustomSharedPreferences;

public class RegisterActivity extends AppCompatActivity {
    private final static String SHARED_PREF_NAME = "loginUserIdPref";
    private final static String USER_ID = "userId";
    private Button btnRegister;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private SharedPreferences preferences;


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
                            "Cont creat",
                            Toast.LENGTH_LONG)
                            .show();
                    CustomSharedPreferences.setIdToPreferences(getApplicationContext(),
                            SHARED_PREF_NAME, result.getId());
//                    setIdToPreferences(result.getId());
                }
            }
        }.execute(user);
    }

    private void setIdToPreferences(long id) {
        preferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(USER_ID, id);
        editor.commit();
    }
}
