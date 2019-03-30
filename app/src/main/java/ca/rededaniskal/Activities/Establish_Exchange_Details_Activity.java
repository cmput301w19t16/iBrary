package ca.rededaniskal.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.rededaniskal.BusinessLogic.Establish_Exchange_Logic;
import ca.rededaniskal.Database.Write_Exchange_DB;
import ca.rededaniskal.Database.Write_Request_DB;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.R;

public class Establish_Exchange_Details_Activity extends AppCompatActivity {

    public static final String LOCATION_SET = "Location Set";
    private static final String TAG = "EstablishExchange";

    private Button btnDatePicker;
    private Button btnTimePicker;
    private Button confirmDetails;
    private Button chooseLoc;
    private EditText txtDate;
    private EditText txtTime;
    private TextView displayLoc;
    private String dayMonthYear;
    private String timePicked;
    private BorrowRequest request;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;
    private String mode;
    private Calendar c;
    private Establish_Exchange_Logic logic;

    //private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establish_location);

        logic = new Establish_Exchange_Logic();

        request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");
        mode = request.getStatus();

        //Set toolbar stuff
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book Exchange Details");

        // Assign buttons and edittexts of UI
        btnDatePicker = (Button) findViewById(R.id.ExchangeDateButton);
        btnTimePicker = (Button) findViewById(R.id.ExchangeTimeButton);
        confirmDetails = (Button) findViewById(R.id.ConfirmExchangeButton);
        chooseLoc = findViewById(R.id.chooseLoc);
        txtDate = (EditText) findViewById(R.id.PickUpDateEditText);
        txtTime = (EditText) findViewById(R.id.PickUpTimeEditText);
        displayLoc = findViewById(R.id.displayLoc);

        txtDate.setEnabled(false);
        txtTime.setEnabled(false);

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
                        R.style.DatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                dayMonthYear = String.format("%02d-%02d-%4d", dayOfMonth,(monthOfYear + 1), year);
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
                        R.style.TimePickerDialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timePicked = String.format("%02d:%02d", hourOfDay,minute);
                                txtTime.setText(timePicked);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        confirmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Calendar chose = Calendar.getInstance();
                chose.set(mYear + 1900, mMonth, mDay, mHour, mMinute);
                if (validDate(chose) && txtTime.getText().length() > 0 && txtDate.getText().length() >0){

                    //Set up string to parse to get the time stamp
                    String dayPart = txtDate.getText().toString();
                    String timePart = "-".concat(txtTime.getText().toString());
                    String timeStr = dayPart.concat(timePart);

                    Date timeStamp = new Date();
                    //Set the format and pase
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-HH:mm");
                    try{

                        timeStamp = simpleDateFormat.parse(timeStr);
                        request.setTimestamp(timeStamp);

                        Exchange exchange = new Exchange(request.getrecipientUID(),
                                request.getsenderUID(), request.getIsbn(), request.getBookId(), request.getLat(), request.getLng(), request.getTimestamp() ) ;

                        Write_Exchange_DB exchange_db = new Write_Exchange_DB();
                        exchange_db.addExchange(exchange);

                        Write_Request_DB db = new Write_Request_DB(request, true);

                        Intent intent = new Intent(getApplicationContext(), View_Exchange_Details_Activity.class);
                        intent.putExtra("exchange", exchange);

                        startActivity(intent);

                    } catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Problem with parsing",Toast.LENGTH_SHORT).show();
                    }
                }

               else{
                    Toast.makeText(getApplicationContext(),"Please make sure you have entered a valid date and time.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        chooseLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Set_Location_Activity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private boolean validDate(Calendar givenCal){
        Calendar cal = Calendar.getInstance();
        //return (givenCal.compareTo(cal) < 0);
        return givenCal.after(cal);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                //Set the latitude and longitude of the request
                Bundle b = data.getExtras();
                double lat = b.getDouble("lat");
                double lng = b.getDouble("lng");
                request.setLat(lat);
                request.setLng(lng);

                displayLoc.setText(LOCATION_SET);

            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
}
