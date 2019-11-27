package eu.ase.damapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {
    private TextView form_tv_date_theoretical;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar timeNow;
    private int year, day, month;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        initComponents();

    }

    private void initComponents() {
        form_tv_date_theoretical = findViewById(R.id.form_tv_date_theoretical);
        form_tv_date_theoretical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeNow = Calendar.getInstance();
                year = timeNow.get(Calendar.YEAR);
                month = timeNow.get(Calendar.MONTH);
                day = timeNow.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        FormActivity.this,
                        android.R.style.Theme_DeviceDefault_Dialog,
                        onDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        spinner = findViewById(R.id.form_spinner);
        ArrayAdapter<CharSequence> licenceCategories =
                ArrayAdapter.createFromResource(getApplicationContext(),
                        R.array.form_licence_options,
                        R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(licenceCategories);

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                form_tv_date_theoretical.setText(date);
            }
        };
    }


}
