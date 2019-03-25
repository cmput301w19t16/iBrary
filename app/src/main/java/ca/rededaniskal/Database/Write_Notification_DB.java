package ca.rededaniskal.Database;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import ca.rededaniskal.EntityClasses.Friend_Request;
import ca.rededaniskal.EntityClasses.Notification;

import static android.support.constraint.Constraints.TAG;

public class Write_Notification_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private Notification notification;


    public Write_Notification_DB(Notification notification) {
        this.notification = notification;
    }


    private void getCurrentUID() {
        Log.d(TAG, "*!*!* In Add_Remove_Friend_DB");

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

    private void removeNotification(){}

    private void updateSeen(boolean seen){}
}




