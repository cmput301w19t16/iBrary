/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View all your requests
 *
 * ISSUES:
 * Needs DB support
 *
 */
package ca.rededaniskal.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.AllRequestsAdapter;
import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

/**
 * @author Daniela, Revan
 */

public class View_All_Requests_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AllRequestsAdapter adapter;
    private ArrayList<Request> requestList = new ArrayList<Request>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: DB load the libary of requested books for the current user

        //For testing
//        Request request1 = new Request("Daniela", "Skye", "book");
//        Request request2 = new Request("Daniela", "Nick", "book");
//        requestList.add(request1);
//        requestList.add(request2);

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


        Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests");

        query.addListenerForSingleValueEvent(valueEventListener2);



    }
    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange2");
            requestList.clear();
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                    requestList.add(request);
                }
                adapter.notifyDataSetChanged();

                Log.d(TAG, "*********----->length"+requestList.size());
//                    requestAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
