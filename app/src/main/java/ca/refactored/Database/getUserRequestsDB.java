package ca.refactored.Database;

/**
 * Author: Delaney
 * Purpose: Get's relevant requests for user to display in notification.
 * Builds a list of request objects where current user is either the sender or recipient
 * on construction.
 *
 * TODO: In the logic that handles the created lists, it should remove the requests where the user
 * was the sender, where the status is not accepted.
 */



import android.util.Log;

import ca.refactored.Activities.Fragments.Notifications_Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.refactored.EntityClasses.Request;

import static android.content.ContentValues.TAG;

public class getUserRequestsDB{
    private FirebaseAuth mAuth;
    private String email;
    private String username;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private List<Request> requestList;
    private Boolean failed;
    private Notifications_Fragment parent;
    private String UID;


    //This function retrieves the users requests from the database.

    public getUserRequestsDB(Notifications_Fragment parent) {
        this.parent = parent;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        requestList = new ArrayList<Request>();
        if (user != null) {
            UID = user.getUid();
            getUserFriendRequestSender();
            failed = false;


        } else {
            failed = true;

        }
    }

    public List<Request> getRequestList() {
        return requestList;
    }


    // Collects friend requests sent by current user
    private void getUserFriendRequestSender(){
        Query query = FirebaseDatabase.getInstance().getReference("FriendRequests")
                .orderByChild("senderUID")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(valueEventListener1);

    }

    ValueEventListener valueEventListener1 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange: Friend Requests Sender");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->Does Exist");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request request = snapshot.getValue(Request.class);
                    requestList.add(request);
                }
            }
            getUserFriendRequestRecipient();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    //Gets the recipient of the request

    private void getUserFriendRequestRecipient(){
        Query query = FirebaseDatabase.getInstance().getReference("Requests")
                .orderByChild("recipientUID")
                .equalTo(UID);

        Log.d(TAG, "*********----->"+username);
        query.addListenerForSingleValueEvent(valueEventListener2);

    }

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange: Friend Requests Recipient");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->Snapshot exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Request request = snapshot.getValue(Request.class);
                    requestList.add(request);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}