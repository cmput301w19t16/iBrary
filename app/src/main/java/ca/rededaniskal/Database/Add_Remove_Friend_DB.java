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

import ca.rededaniskal.EntityClasses.Friend_Request;
import ca.rededaniskal.EntityClasses.Friendship;
import ca.rededaniskal.EntityClasses.User;

import static android.support.constraint.Constraints.TAG;

public class Add_Remove_Friend_DB {

    private Friend_Request friend_request;
    private String friend_uid;
    private boolean delete;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private String UID;
    private List<String> keys;

    public Add_Remove_Friend_DB(Friend_Request friend_request){
        this.friend_request = friend_request;
        delete = false;
        getCurrentUID();
    }

    public Add_Remove_Friend_DB(String friend_uid){
        this.friend_uid = friend_uid;
        delete = true;
        getCurrentUID();
    }

    private void getCurrentUID() {
        Log.d(TAG, "*!*!* In Add_Remove_Friend_DB");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();

            if(delete){
                getFriendship1();
            }else{
                createFriendRequest();
            }

        }
    }

    private void getFriendship1(){
        Log.d(ContentValues.TAG, "*********----->getFriendship");
        Query query = FirebaseDatabase.getInstance().getReference("FriendRequests")
                .orderByChild("friend1")
                .equalTo(friend_uid);

        Log.d(ContentValues.TAG, "*********----->Friend UID: "+friend_uid);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            keys.clear();
            Log.d(ContentValues.TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(ContentValues.TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Friendship fre = snapshot.getValue(Friendship.class);
                    if(fre.getFriend2().equals(UID)) {
                        keys.add(snapshot.getKey());
                    }

                }
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void createFriendRequest(){
        mDatabase = FirebaseDatabase.getInstance().getReference("FriendRequests");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(friend_request);
    }

}
