package ca.rededaniskal.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

/**
 * @author Daniela
 * Display details about a book pick up.
 */
public class View_Exchange_Details_Activity extends AppCompatActivity {
    TextView location;
    TextView dateTime;
    Button goToScanner;
    String mode;
    TextView viewExchangeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final BorrowRequest request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");

        int month = getIntent().getIntExtra("Month", -1);
        int day = getIntent().getIntExtra("Day", -1);
        int year = getIntent().getIntExtra("Year", -1);

        int hour = getIntent().getIntExtra("Hour", -1);
        int minute = getIntent().getIntExtra("Minute", -1);

        //String dateTimeStr = getIntent().getStringExtra("D/M/Y");

        setContentView(R.layout.activity_view__pick_up__details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        viewExchangeDetails = findViewById(R.id.ViewExchangeDetailsTextView);

        mode = request.getStatus();
        if (mode == "Accepted"){
            viewExchangeDetails.setText(R.string.view_pick_up_details);
        } else{
            viewExchangeDetails.setText(R.string.view_drop_off_details);
        }

        dateTime = findViewById(R.id.DateTimePickUpTextView);
        location = findViewById(R.id.LocationPickUpTextView);
        goToScanner = findViewById(R.id.ScanBookPickUpButton);

        //SimpleDateFormat dateTimeFormatted = new SimpleDateFormat("E, MMM d, yyyy").format(dateTimeStr);

        //dateTime.setText(dateTimeFormatted + ", " + Integer.toString(hour) + Integer.toString(minute));
        String monthWord = getMonthFromInt(month);
        String time = String.format("%2d:%02d", hour,minute);
        dateTime.setText(monthWord + " "+ String.valueOf(day) + "," + time);
        location.setText("at");

       goToScanner.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), Barcode_Scanner_Activity.class);
               intent.putExtra("ReturnClass", View_Exchange_Details_Activity.class);
               startActivityForResult(intent, 1);
           }
       });


    }
    //http://bluebones.net/2002/10/converting-month-number-to-month-name-in-java/
    private String getMonthFromInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11 ) {
            month = months[m];
        }
        return month;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            //do something with ISBN to change the status of the book
        }
    }

}
