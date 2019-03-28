package ca.rededaniskal.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

/**
 * @author Daniela
 * Display details about a book pick up.
 */
public class View_Exchange_Details_Activity extends Activity implements OnMapReadyCallback {
    TextView location;
    TextView dateTime;
    Button goToScanner;
    String mode;
    TextView viewExchangeDetails;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pick_up__details);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.
                        map);
        mapFragment.getMapAsync(this);

        final BorrowRequest request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");




        String timePicked = getIntent().getStringExtra("TimePicked");
        String dateMonthYear = getIntent().getStringExtra("D/M/Y");
        int month = getIntent().getIntExtra("Month", -1);
        int day = getIntent().getIntExtra("Day", -1);
        int year = getIntent().getIntExtra("Year", -1);

        int hour = getIntent().getIntExtra("Hour", -1);
        int minute = getIntent().getIntExtra("Minute", -1);


        Toolbar toolbar = findViewById(R.id.toolbar);


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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
