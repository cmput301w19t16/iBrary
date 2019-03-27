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

import java.util.List;

import ca.rededaniskal.EntityClasses.Friend_Request;
import ca.rededaniskal.EntityClasses.Friendship;

import ca.rededaniskal.EntityClasses.User;

import static android.support.constraint.Constraints.TAG;

public class Write_Friendship_DB {

    private Friend_Request friend_request;
    private String friend_uid;
    private boolean delete;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private String key;

    private boolean isFollowed;


    public Write_Friendship_DB(Friend_Request friend_request){
        this.friend_request = friend_request;
        delete = false;
        getCurrentUID();
    }

    public Write_Friendship_DB(String friend_uid){
        this.friend_uid = friend_uid;
        delete = true;
        getCurrentUID();
    }

    private void getCurrentUID() {
        Log.d(TAG, "*!*!* In Write_Friendship_DB");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();

//            if(delete){
//                deleteFriendship();
//            }else{
//                addFriendship();
//            }

        }
    }


    public boolean isFollowing(String follower, final String leader){
        isFollowed = false;

        Query query = FirebaseDatabase.getInstance().getReference("Friendships")
                .orderByChild("leader")
                .equalTo(follower);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Friendship fre = snapshot.getValue(Friendship.class);
                        if(fre.getFollower().equals(leader)) {
                            key = snapshot.getKey();
                            isFollowed = true;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return isFollowed;
    }



    private void deleteFriendship(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Friendships");
        mDatabase.child(key).removeValue();
    }


    private void addFriendship(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Friendships");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(friend_request);
    }

}
