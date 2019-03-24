package ca.rededaniskal.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.lang.reflect.Field;
import java.util.Calendar;

import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

public class Establish_PickUp_Details_Activity extends AppCompatActivity {

    Button btnDatePicker;
    Button btnTimePicker;
    Button confirmDetails;
    EditText txtDate;
    EditText txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establish_location);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        final BorrowRequest request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");


        btnDatePicker = (Button) findViewById(R.id.PickUpDateButton);
        btnTimePicker = (Button) findViewById(R.id.PickUpTimeButton);
        txtDate = (EditText) findViewById(R.id.PickUpDateEditText);
        txtTime = (EditText) findViewById(R.id.PickUpTimeEditText);

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateTime = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                txtDate.setText(dateTime);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getApplicationContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                txtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        confirmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(getApplicationContext(), View_PickUp_Details_Activity.class);
                intent.putExtra("BorrowRequestObj", request);
                intent.putExtra("Hour",mHour);
                intent.putExtra("Minute", mMinute);
                intent.putExtra("dateTime", dateTime);
                startActivity(intent);
            }
        });
    }
}






