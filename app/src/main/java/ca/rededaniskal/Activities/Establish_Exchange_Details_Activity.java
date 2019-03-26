package ca.rededaniskal.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

public class Establish_Exchange_Details_Activity extends AppCompatActivity {

    private Button btnDatePicker;
    private Button btnTimePicker;
    private Button confirmDetails;
    private EditText txtDate;
    private EditText txtTime;
    private TextView exchangeType;
    private String dayMonthYear;
    private String timePicked;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String mode;
    private Calendar c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establish_location);
        exchangeType = findViewById(R.id.EstablishExchangeTypeTextView);

        final BorrowRequest request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");
        mode = request.getStatus();

        if (mode == "Accepted"){
            exchangeType.setText(R.string.select_pick_up_dets);
        } else{
            exchangeType.setText(R.string.select_drop_off_dets);
        }

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



        btnDatePicker = (Button) findViewById(R.id.ExchangeDateButton);
        btnTimePicker = (Button) findViewById(R.id.ExchangeTimeButton);
        confirmDetails = (Button) findViewById(R.id.ConfirmExchangeButton);
        txtDate = (EditText) findViewById(R.id.PickUpDateEditText);
        txtTime = (EditText) findViewById(R.id.PickUpTimeEditText);

        c = Calendar.getInstance();
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                //final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Establish_Exchange_Details_Activity.this,
                        android.R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dayMonthYear = String.format("%2d-%02d-%4d", dayOfMonth,(monthOfYear + 1), year);
                                txtDate.setText(dayMonthYear);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                // Get Current Time
                //final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Establish_Exchange_Details_Activity.this,
                        R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timePicked = String.format("%2d:%02d", hourOfDay,minute);
                                txtTime.setText(timePicked);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
                //timePickerDialog.showAtLocation(setLayout(R.layout.activity_establish_location), Gravity.CENTER, 0, 0);
            }
        });

        confirmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Calendar chose = Calendar.getInstance();
                chose.set(mYear + 1900, mMonth, mDay, mHour, mMinute);
                if (validDate(chose) && txtTime.getText() != null && txtDate.getText() != null){
                    Intent intent = new Intent(getApplicationContext(), View_Exchange_Details_Activity.class);
                    intent.putExtra("TimePicked", timePicked);
                    intent.putExtra("BorrowRequestObject", request);
                    intent.putExtra("Hour",mHour);
                    intent.putExtra("Minute", mMinute);
                    intent.putExtra("Day", mDay);
                    intent.putExtra("Month",mMonth);
                    intent.putExtra("Year", mYear);
                    intent.putExtra("D/M/Y", dayMonthYear);
                    startActivity(intent);
                }
               else{
                    Toast.makeText(getApplicationContext(),"Please make sure you have entered a valid date and time.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validDate(Calendar givenCal){
        Calendar cal = Calendar.getInstance();
        //return (givenCal.compareTo(cal) < 0);
        return givenCal.after(cal);
    }
}






