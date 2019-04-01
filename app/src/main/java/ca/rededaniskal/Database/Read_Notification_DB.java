package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;

import ca.rededaniskal.Activities.Fragments.Notifications_Fragment;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.User;

import static android.support.constraint.Constraints.TAG;

public class Read_Notification_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private Notification notification;
    private String key;
    private String RequestID;
    private Notifications_Fragment parent;


    public Read_Notification_DB(Notifications_Fragment parent) {
        this.parent = parent;
        getCurrentUID();
    }

    private void getCurrentUID() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            readAllUserNotifications();
        }
    }

    private void readAllUserNotifications() {
        Query query = FirebaseDatabase.getInstance().getReference("Notifications")
                .orderByChild("userID")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Notification notification = snapshot.getValue(Notification.class);
                        parent.addAndUpdate(notification);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
