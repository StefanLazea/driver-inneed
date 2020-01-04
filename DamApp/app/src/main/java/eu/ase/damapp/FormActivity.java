package eu.ase.damapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import eu.ase.damapp.util.CustomSharedPreferences;
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
    private DatabaseReference mDatabase;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        userId = String.valueOf(CustomSharedPreferences
                .getIdFromPreferences(getApplicationContext(),
                        RegisterActivity.SHARED_PREF_NAME));
        initAttributes();
        btnSend.setEnabled(false);
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

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Form form = data.getValue(Form.class);
                        if (form.getId().equals("details" + userId)) {
                            getInfo();
                        } else {
                            btnSend.setEnabled(true);
                        }
                    }
                } else {
                    btnSend.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFormData()) {
                    Form formResult = createForm();
                    formResult.setId("details" + userId);
                    upsert(formResult);

                    Toast.makeText(getApplicationContext(), "" + mDatabase.push().getKey(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(FormActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getInfo() {
        mDatabase.child("details" + userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Form data = dataSnapshot.getValue(Form.class);
                Log.i("firebase", data + "ura");
                school.setText(data.getSchoolName());
                form_tv_date_practical.setText(new SimpleDateFormat(
                        DATE_FORMAT, Locale.US).format(data.getDatePracticalExam()));
                form_tv_date_theoretical.setText(new SimpleDateFormat(
                        DATE_FORMAT, Locale.US).format(data.getDateTheoreticalExam()));
                spinner.setSelection(((ArrayAdapter<CharSequence>) spinner.getAdapter()).getPosition(data.getLicenceCategory()));
                if (data.isSchoolStarted()) {
                    checkBoxSchool.setChecked(true);
                }
                if (data.getSex().equals("Masculin")) {
                    rgSex.check(R.id.form_rb_masculin);
                } else {
                    rgSex.check(R.id.form_rb_feminin);
                }
                btnSend.setEnabled(true);
                btnSend.setText("Update");
                btnSend.setBackgroundColor(Color.GREEN);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("ReportActivity", "Data is not available");

            }
        });
    }

    public String upsert(final Form form) {
        if (form == null) {
            return null;
        }

        if (form.getId() == null || form.getId().trim().isEmpty()) {
            form.setId(mDatabase.push().getKey());
        }

        mDatabase.child(form.getId()).setValue(form);
        mDatabase.child(form.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Form formLoaded = dataSnapshot.getValue(Form.class);
                if (formLoaded != null) {
                    Log.i(">> RTDB", "S-a facut update " + formLoaded.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(">> RTDB", "SomethingWentWrong");
            }
        });
        return form.getId();
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
