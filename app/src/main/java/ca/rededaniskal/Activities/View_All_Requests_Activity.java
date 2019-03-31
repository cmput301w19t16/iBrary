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

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BorrowRequestAdapter;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

/**
 * @author Daniela, Revan
 */

public class View_All_Requests_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BorrowRequestAdapter adapter;
    private ArrayList<BorrowRequest> requestList = new ArrayList<BorrowRequest>();
    private getAllUserRequests db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //TODO: DB load the libary of requested books for the current user


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__requests_);

        recyclerView = (RecyclerView) findViewById(R.id.DisplayRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Testing purposes
        requestList.add(new BorrowRequest("Daniela", "Nick", "9780590353427", "123456"));

        adapter = new BorrowRequestAdapter(this, requestList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Requests on My Books");

        db = new getAllUserRequests();
    }

    private void returnToLogin() {
        startActivity(new Intent(this, Login_Activity.class));
    }

    //*** Enclosed Database helper class***
    private class getAllUserRequests {
        private String email;
        private FirebaseAuth mAuth;
        private FirebaseUser user;
        private String username;


        //Get all the requests
        public getAllUserRequests() {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            if (user != null) {
                email = user.getEmail();
                getUsername();



            } else {
                returnToLogin();
            }
        }

        private void getUsername() {
            Log.d(TAG, "*********----->/Email: "+email);
            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("email").equalTo(email);

            query.addListenerForSingleValueEvent(valueEventListener1);
        }


        ValueEventListener valueEventListener1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "*********----->onDataChange1");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User u = snapshot.getValue(User.class);
                        Log.d(TAG, "*********----->username: "+u.getUserName());
                        username = u.getUserName();
                        getRequests();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        private void getRequests() {

            Query query2 = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                    .orderByChild("recipientUID").equalTo(username);

            query2.addListenerForSingleValueEvent(valueEventListener3);
        }

        ValueEventListener valueEventListener3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "*********----->onDataChange3");
                requestList.clear();
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                        Log.d(TAG, "*********----->Books "+request.getIsbn());
                        requestList.add(request);
                    }
                    adapter.notifyDataSetChanged();

                    Log.d(TAG, "*********----->length" + requestList.size());
//                    requestAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

}
