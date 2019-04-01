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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Update_Book_DB;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.Database.Write_Exchange_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

/**
 * @author Daniela
 * Display details about a book pick up.
 */
public class View_Exchange_Details_Activity extends  AppCompatActivity  implements OnMapReadyCallback {
    TextView title, owner, dateTime, borrower;

    Button goToScanner;
    String mode;
    private Book_Instance book;
    private User bookOwner, bookBorrower;

    private GoogleMap mMap;

    private Exchange exchange;

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
        borrower = findViewById(R.id.viewborrower);


        goToScanner = findViewById(R.id.ScanBookPickUpButton);

        //get the Request object
        exchange = (Exchange) getIntent().getSerializableExtra("exchange");

        //Set the views
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Book Exchange Details"); //TODO: change this if we want
        getBookInfo();
        getOwnerInfo();
        getBorrowerInfo();


        //Get date in the right format
        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        String date =formatter.format(exchange.getTime());
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

    private void getBookInfo(){
        BookInstanceDb bidb = new BookInstanceDb();
        myCallbackBookInstance mcbi = new myCallbackBookInstance() {
            @Override
            public void onCallback(Book_Instance book_instance) {
                book = book_instance;
                setBookInfo();
            }
        };
        bidb.getBookInstance(exchange.getOwner(), exchange.getBookid(), mcbi);
    }

    private void getOwnerInfo(){
        Users_DB udb = new Users_DB();
        myCallbackUser mcbu = new myCallbackUser() {
            @Override
            public void onCallback(User user) {
                bookOwner = user;
                setOwnerInfo();
            }
        };
        udb.getUser(exchange.getOwner(), mcbu);
    }

    private void setOwnerInfo(){
        owner.setText( bookOwner.getUserName());
    }

    private void getBorrowerInfo(){
        Users_DB udb = new Users_DB();
        myCallbackUser mcbu = new myCallbackUser() {
            @Override
            public void onCallback(User user) {
                bookBorrower = user;
                setBorrowerInfo();
            }
        };
        udb.getUser(exchange.getBorrower(), mcbu);
    }

    private void setBorrowerInfo(){
        borrower.setText( bookBorrower.getUserName());
    }



    private void setBookInfo(){
        title.setText( book.getTitle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            // Once the scan is successful, update information about the exchange and book
            // First, check who just scanned
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();
            if (user != null) {
                String UID = user.getUid();

                // Update their booleans
                if(UID.equals(exchange.getOwner())){
                    exchange.setOwnerScanend(true);
                    // Once owner is scanned, book can be updated to have new possessor and status.
                    if(!exchange.isReturning()){
                        Update_Book_DB db = new Update_Book_DB(exchange.getOwner(), exchange.getBorrower(), exchange.getIsbn());
                    }

                }else{
                    exchange.setBorrowedScanned(true);
                    if(exchange.isReturning()){
                        Update_Book_DB db = new Update_Book_DB(exchange.getOwner(), exchange.getOwner(), exchange.getIsbn());
                    }
                }

                Write_Exchange_DB db = new Write_Exchange_DB();

                // If the book has been scanned by both parties, exchange may be deleted.
                if(exchange.isBorrowedScanned() && exchange.isOwnerScanend()){
                    db.removeExchange(exchange);

                // Otherwise, update the exchange object with the new booleans
                }else{
                    db.updateExchange(exchange);
                }
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng loc = new LatLng(exchange.getLat(), exchange.getLng());
        mMap.addMarker(new MarkerOptions().position(loc).title("Meeting place"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 13));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.animateCamera(zoom);
    }
}
