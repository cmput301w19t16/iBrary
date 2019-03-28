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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.R;

/**
 * @author Daniela
 * Display details about a book pick up.
 */
public class View_Exchange_Details_Activity extends  AppCompatActivity  implements OnMapReadyCallback {
    TextView title, owner, dateTime;

    Button goToScanner;
    String mode;

    private GoogleMap mMap;

    private BorrowRequest  request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pick_up__details);

        //Set up the map
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.
                        map);
        mapFragment.getMapAsync(this);

        //init the views
        title = findViewById(R.id.viewtitle);
        owner = findViewById(R.id.viewOwner);
        dateTime = findViewById(R.id.viewDateTime);


        goToScanner = findViewById(R.id.ScanBookPickUpButton);

        //get the Request object
        request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");

        //Set the views
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book Exchange Details"); //TODO: change this if we want

        title.setText(request.getBookId()); //TODO: get title from dp
        owner.setText(request.getrecipientUID());

        //Get date in the right format
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        String date =formatter.format(request.getTimestamp());
        dateTime.setText(date);


       goToScanner.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), Barcode_Scanner_Activity.class);
               intent.putExtra("ReturnClass", View_Exchange_Details_Activity.class);
               startActivityForResult(intent, 1);
           }
       });


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

        // TODO: put marker in the right spot

        LatLng loc = new LatLng(request.getLat(), request.getLng());
        mMap.addMarker(new MarkerOptions().position(loc).title("Meeting place"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);
    }
}
