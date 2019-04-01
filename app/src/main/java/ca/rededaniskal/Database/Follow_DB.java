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

import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.User;

public class Follow_DB {

    private DatabaseReference mDatabase;

    public Follow_DB(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void isFollowing(String follower, final String leader, final myCallbackBool myCallback){
        Query query = mDatabase.child("followings/" + follower);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    if (dataSnapshot.hasChild(leader)) {
                        myCallback.onCallback(true);
                    }
                    else{
                        myCallback.onCallback(false);
                    }
                }
                else {
                    myCallback.onCallback(false);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }

    public void getLeaders(String follower, final myCallbackStringList mcbul){
        Query query = mDatabase.child("followings/" + follower);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        arrayList.add(snapshot.getKey());
                    }
                    mcbul.onCallback(arrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }

    public void getFollowers(final String leader, final myCallbackStringList mcbul){
        Query query = mDatabase.child("followings");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> arrayList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if (snapshot.hasChild(leader)){
                            arrayList.add(snapshot.getKey());
                        }
                    }
                    mcbul.onCallback(arrayList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }


    public void swapFollow(String follower, String leader, boolean isFollowed){
        if (isFollowed){
            unfollow(follower, leader);
        }
        else{
            follow(follower, leader);
        }
    }

    private void unfollow(String follower, String leader){
        mDatabase.child("followings/" + follower + "/" + leader).removeValue();
        //mDatabase.removeValue();
        changeFollowCount(-1, leader);
        Notifications_DB nb = new Notifications_DB();
        nb.deleteNotification(follower + "/" + leader);
    }

    private void follow(String follower, String leader){
        mDatabase.child("followings/" + follower).child(leader).setValue(leader);
        //mDatabase.child(leader).setValue(leader);
        changeFollowCount(1, leader);
        Notification notification = new Notification(leader, follower, follower + "/" + leader, "Friend Request");
        Notifications_DB nb = new Notifications_DB();
        nb.storeNotification(notification);
    }

    private void changeFollowCount(final int i, String leader){
        Users_DB udb = new Users_DB();
        myCallbackUser mcbu = new myCallbackUser() {
            @Override
            public void onCallback(User user) {
                setFollowCount(i, user);
            }
        };
        udb.getUser(leader, mcbu);
    }

    private void setFollowCount(int i, User u){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(
                "Users/" + u.getUID());
        int followcount = i + u.getFollowerCount();
        ref.child("followerCount").setValue(followcount);
    }
}
