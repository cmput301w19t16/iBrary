package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.myCallbackNotiList;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.EntityClasses.Notification;

public class Notifications_DB {
    private DatabaseReference mDatabase;

    public Notifications_DB(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getUsersNotifications(String uid, final myCallbackNotiList mcbnl){
        Query query = mDatabase.child("Notifications").orderByChild("userID").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Notification> notiList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        notiList.add(snapshot.getValue(Notification.class));
                    }
                    mcbnl.onCallback(notiList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }


    //deletes all notifications with request == reqID
    public void deleteNotification(String reqID){
        Query query = mDatabase.child("Notifications").orderByChild("request").equalTo(reqID);
        final myCallbackStringList mcbsl = new myCallbackStringList() {
            @Override
            public void onCallback(ArrayList<String> strList) {
                for (String str : strList){
                    mDatabase.child("Notifications/" + str).removeValue();
                }

            }
        };
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> al = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        al.add(snapshot.getKey());
                    }
                    mcbsl.onCallback(al);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }

    public void storeNotification(Notification notification){
        DatabaseReference key = mDatabase.child("Notifications").push();
        key.setValue(notification);
    }
}
