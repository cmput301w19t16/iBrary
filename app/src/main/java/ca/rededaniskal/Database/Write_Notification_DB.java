package ca.rededaniskal.Database;

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

import java.util.List;

import ca.rededaniskal.EntityClasses.Notification;

import static android.support.constraint.Constraints.TAG;

public class Write_Notification_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private Notification notification;
    private String key;
    private String RequestID;
    private boolean delete;

    public Write_Notification_DB(Notification notification) {
        this.notification = notification;
        addNotification();
    }

    public Write_Notification_DB(String RequestID) {
        Log.d(TAG, "*!*!* In Write_Notification_DB");
        this.RequestID = RequestID;
        this.delete = true;
        getNotificationKey();

    }

    public Write_Notification_DB(){
        this.delete = false;
    }

    private void getCurrentUID() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();

        }
    }

    private void addNotification(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
        String k = mDatabase.push().getKey();
        mDatabase.child(k).setValue(notification);
    }

    public void getNotificationKey(){
        Log.d(ContentValues.TAG, "*********----->getNotificationKey");
        Query query = FirebaseDatabase.getInstance().getReference("Notifications")
                .orderByChild("request")
                .equalTo(RequestID);

        Log.d(ContentValues.TAG, "*********----->requestID: "+RequestID);
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
                    key = snapshot.getKey();

                }
                if (delete){
                    removeNotification(key);
                }else{
                    updateNotification(key);
                }
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void removeNotification(String key){
        Log.d(ContentValues.TAG, "*********----->removeNotification");
        mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
        mDatabase.child(key).removeValue();
    }

    private void updateNotification(String key){
        Log.d(ContentValues.TAG, "*********----->updateNotification");
        mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
        mDatabase.child(key).setValue(notification);
    }

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String requestID) {
        RequestID = requestID;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}




