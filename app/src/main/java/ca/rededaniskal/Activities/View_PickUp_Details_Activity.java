package ca.rededaniskal.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

/**
 * @author Daniela
 * Display details about a book pick up.
 */
public class View_PickUp_Details_Activity extends AppCompatActivity {
    TextView location;
    TextView dateTime;
    String dateTimeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        final BorrowRequest request = (BorrowRequest) getIntent().getSerializableExtra("BorrowRequestObject");
        dateTimeStr = getIntent().getStringExtra("dateTime");

        int hour = getIntent().getIntExtra("Hour", -1);
        int minute = getIntent().getIntExtra("Minute", -1);

        dateTime = findViewById(R.id.DateTimePickUpTextView);
        location = findViewById(R.id.LocationPickUpTextView);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM, dd ");
        System.out.println(sdf.format(new SimpleDateFormat("yyyy-M-dd").parse(s)));df
        dateTime.setText();



    }

}
