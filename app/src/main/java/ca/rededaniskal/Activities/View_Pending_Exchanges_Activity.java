package ca.rededaniskal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;

import ca.rededaniskal.BusinessLogic.Book_ExchangeAdapter;
import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.EntityClasses.Book_Exchange;


import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.R;

public class View_Pending_Exchanges_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Book_ExchangeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__pending__exchanges_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pending Exchanges");

        ArrayList<Book_Exchange> exchanges = new ArrayList<>(); //TODO: get from DB
        Date d = new Date();


        Book_Exchange e = new Exchange("Revan", "Nick", "12345", "12345", 37.0, 67.0, d);
        exchanges.add(e);

        recyclerView = (RecyclerView) findViewById(R.id.Display);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Book_ExchangeAdapter(this, exchanges);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
