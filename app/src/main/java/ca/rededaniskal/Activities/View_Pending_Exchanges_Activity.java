package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ca.rededaniskal.BusinessLogic.Book_ExchangeAdapter;
import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.Database.Read_Exchange_DB;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.Exchange;

import ca.rededaniskal.R;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * This activity displays 
 */

public class View_Pending_Exchanges_Activity extends AppCompatActivity {

    private static final String TAG = "View_Pending_Exchanges_Activity";
    private RecyclerView recyclerView;
    private Book_ExchangeAdapter adapter;
    ArrayList<Exchange> exchanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pending__exchanges_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Exchanges");

         //TODO: get from DB
        Date d = new Date();
        exchanges = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.Display);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "%*%*%  This null? " + exchanges);
        adapter = new Book_ExchangeAdapter(this, exchanges);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Read_Exchange_DB db = new Read_Exchange_DB(this);
        db.getUserExchanges();
    }

    public void returnToLogin() {
        startActivity(new Intent(View_Pending_Exchanges_Activity.this, Login_Activity.class));
    }

    public void updateAdapter(ArrayList<Exchange> exchanges){
        Log.d(TAG, "%*%*%  Or This null? " + exchanges);
        adapter = new Book_ExchangeAdapter(this, exchanges);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
