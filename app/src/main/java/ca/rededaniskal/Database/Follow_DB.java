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

import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.EntityClasses.User;

public class Follow_DB {

    private DatabaseReference mDatabase;

    public Follow_DB(){
    }

    public void isFollowing(String follower, final String leader, final myCallbackBool myCallback){
        Query query = FirebaseDatabase.getInstance().getReference("followings/" + follower)
                .child(leader);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    myCallback.onCallback(true);
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

    public void swapFollow(String follower, String leader, boolean isFollowed){
        if (isFollowed){
            unfollow(follower, leader);
        }
        else{
            follow(follower, leader);
        }
    }

    private void unfollow(String follower, String leader){
        mDatabase = FirebaseDatabase.getInstance().getReference("followings/" + follower
            + "/" + leader);
        mDatabase.removeValue();
        changeFollowCount(-1, leader);
    }

    private void follow(String follower, String leader){
        mDatabase = FirebaseDatabase.getInstance().getReference("followings/"+follower);
        mDatabase.child(leader).setValue(leader);
        changeFollowCount(1, leader);
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
        ref.child("followerCount").setValue(i + u.getFollowerCount());
    }
}
