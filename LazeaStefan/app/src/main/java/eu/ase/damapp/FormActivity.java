package eu.ase.damapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eu.ase.damapp.util.Form;

public class FormActivity extends AppCompatActivity {
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private TextView form_tv_date_theoretical;
    private TextView form_tv_date_practical;
    private Button btnSend;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePickerDialog.OnDateSetListener onDatePracticalSetListener;
    private Calendar timeNow;
    private int year, day, month;
    private Spinner spinner;
    private CheckBox checkBoxSchool;
    private TextInputEditText school;
    private RadioGroup rgSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initAttributes();
        initComponents();
    }

    private void initAttributes() {
        form_tv_date_theoretical = findViewById(R.id.form_tv_date_theoretical);
        form_tv_date_practical = findViewById(R.id.form_date_exam_practical);
        rgSex = findViewById(R.id.form_rg_sex);
        checkBoxSchool = findViewById(R.id.form_check_school);
        btnSend = findViewById(R.id.form_btn);
        school = findViewById(R.id.form_input_school);
        timeNow = Calendar.getInstance();
        year = timeNow.get(Calendar.YEAR);
        month = timeNow.get(Calendar.MONTH);
        day = timeNow.get(Calendar.DAY_OF_MONTH);
    }

    private void initComponents() {
        setDateTime(form_tv_date_theoretical, "theoretical");

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "-" + dayOfMonth + "-" + year;
                form_tv_date_theoretical.setText(date);
            }
        };


        setDateTime(form_tv_date_practical, "practical");

        onDatePracticalSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "-" + dayOfMonth + "-" + year;
                form_tv_date_practical.setText(date);
            }
        };

        spinner = findViewById(R.id.form_spinner);
        ArrayAdapter<CharSequence> licenceCategories =
                ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.form_licence_options,
                        R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(licenceCategories);

        rgSex = findViewById(R.id.form_rg_sex);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFormData()) {
                    Form formResult = createForm();
                    Toast.makeText(getApplicationContext(), "" + formResult, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FormActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }


    private Form createForm() {
        Date dateTheoretical = convertStringToDate(form_tv_date_theoretical.getText().toString());
        Date datePractical = convertStringToDate(form_tv_date_practical.getText().toString());

        String schoolName = school.getText().toString();
        String licenceCategory = spinner.getSelectedItem().toString();

        boolean schoolStarted = false;
        if (checkBoxSchool.isChecked()) {
            schoolStarted = true;
        }
        RadioButton rb = findViewById(rgSex.getCheckedRadioButtonId());
        String sex = rb.getText().toString();
        return new Form(schoolName, licenceCategory, dateTheoretical, datePractical, sex, schoolStarted);
    }

    private Date convertStringToDate(String date) {
        Date dateTheoretical = null;
        try {
            dateTheoretical = new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTheoretical;
    }

    private boolean validateFormData() {
        if (school.getText() != null && school.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    R.string.form_school_error,
                    Toast.LENGTH_LONG)
                    .show();
            return false;
        }
        RadioButton rb = findViewById(rgSex.getCheckedRadioButtonId());
        if(rb == null){
            Toast.makeText(getApplicationContext(),
                    R.string.form_rb_sex_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void setDateTime(final TextView textView, final String listenerType) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        listenerType.equals("theoretical") ? onDateSetListener : onDatePracticalSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
    }


}
