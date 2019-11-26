package eu.ase.damapp.fragment;


import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import eu.ase.damapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FormStudentFragment extends Fragment {
    private TextView dateTheoretical;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private Calendar timeNow;
    private int year, day, month;

    public FormStudentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_form_student, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        dateTheoretical = view.findViewById(R.id.form_tv_date_theoretical);


        dateTheoretical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeNow = Calendar.getInstance();
                year = timeNow.get(Calendar.YEAR);
                month = timeNow.get(Calendar.MONTH);
                day = timeNow.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        android.R.style.Theme_DeviceDefault_Dialog,
                        onDateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                dateTheoretical.setText(date);
            }
        };
    }

}
