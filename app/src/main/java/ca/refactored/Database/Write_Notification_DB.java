package ca.refactored.Database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.refactored.EntityClasses.Notification;

import static android.support.constraint.Constraints.TAG;

public class Write_Notification_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private Notification notification;
    private boolean delete;


    public Write_Notification_DB(Notification notification) {
        this.notification = notification;
    }

    public Write_Notification_DB(Notification notification, boolean delete) {
        this.notification = notification;
        this.delete = delete;
    }


    private void getCurrentUID() {
        Log.d(TAG, "*!*!* In Write_Notification_DB");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();

        }
    }

    private void addNotification(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(notification);
    }

    private void getNotificationKey(){
        String reqID = notification.getRequest();
        Log.d(ContentValues.TAG, "*********----->getNotificationKey");
        Query query = FirebaseDatabase.getInstance().getReference("Notifications")
                .orderByChild("requestID")
                .equalTo(reqID);

        Log.d(ContentValues.TAG, "*********----->requestID: "+reqID);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ContentValues.TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(ContentValues.TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification notif = snapshot.getValue(Notification.class);
                    String key = snapshot.getKey();

                }
            }
            if(delete){
                removeNotification();
            }else{
                updateNotification();
            }

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void removeNotification(){
        Log.d(ContentValues.TAG, "*********----->removeNotification");
    }

    private void updateNotification(){
        Log.d(ContentValues.TAG, "*********----->updateNotification");
    }
}




