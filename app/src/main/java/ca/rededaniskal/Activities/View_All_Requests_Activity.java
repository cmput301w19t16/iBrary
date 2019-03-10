package ca.rededaniskal.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.AllRequestsAdapter;
import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.R;

/**
 * @author Daniela, Revan
 */

public class View_All_Requests_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllRequestsAdapter adapter;
    private ArrayList<Request> requestList = new ArrayList<Request>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For testing
        //TODO: DB load the libary of requested books for the current user

        Request request1 = new Request("Daniela", "Skye", "book");
        Request request2 = new Request("Daniela", "Nick", "book");
        requestList.add(request1);
        requestList.add(request2);

       // Book_Instance HP = new Book_Instance("HappyPotter", "JK", "123", "Revan", "Daniela", "Very Nice", "Requested");
        //BL.addBook(HP);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__requests_);

        recyclerView = (RecyclerView) findViewById(R.id.DisplayRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AllRequestsAdapter(this, requestList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

}
