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

import java.util.ArrayList;

import ca.rededaniskal.Activities.Fragments.Post_Feed_Fragment;
import ca.rededaniskal.BusinessLogic.myCallbackUidList;
import ca.rededaniskal.EntityClasses.Post;

import static android.support.constraint.Constraints.TAG;

public class Write_Post_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UID;
    private ArrayList<String> followers;
    private myCallbackUidList mcbuid;
    private Follow_DB fdb;
    private DatabaseReference mDatabase;
    private Post post;
    private ArrayList<Post> posts;
    private Post_Feed_Fragment parent;

    public Write_Post_DB(Post post) {
        this.post = post;
    }

    public Write_Post_DB(Post_Feed_Fragment parent){
        this.UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        posts = new ArrayList<>();
        this.parent = parent;
        readHomeFeed();
    }

    public void addPostToFollowersFeed(){
        fdb = new Follow_DB();
        followers = new ArrayList<>();
        getCurrentUID();
    }

    private void getCurrentUID() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            Log.d(TAG, "^*^*^*^ GOT UID: " + UID);
            getFollowers();

        }
    }

    private void getFollowers(){
        Log.d(TAG, "^*^*^*^ In GET FOLLOWERS");
        mcbuid = new myCallbackUidList() {
            @Override
            public void onCallback(ArrayList<String> uidList) {
                Log.d(TAG, "^*^*^*^ ON CALLBACK");
                if (uidList!= null) {
                    Log.d(TAG, "^*^*^*^ FOLLOWER LIST: " + uidList);
                    followers = uidList;
                    addPostToFeeds();
                }
            }
        };
        fdb.getFollowers(UID, mcbuid);
    }

    private void addPostToFeeds(){
        for(int i = 0; i < followers.size(); i++) {
            String follower = followers.get(i);
            Log.d(TAG, "^*^*^*^ Adding to follower: " + follower);
            mDatabase = FirebaseDatabase.getInstance().getReference("home-feed")
                    .child(follower);
            String key = mDatabase.push().getKey();
            mDatabase.child(key).setValue(post);
        }
    }



    private void readHomeFeed(){
        Log.d(ContentValues.TAG, "*********----->In READHOMEFEED");
        Query query = FirebaseDatabase.getInstance().getReference("home-feed/" + UID);
        Log.d(ContentValues.TAG, "*********----->UID: " + UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post post = snapshot.getValue(Post.class);
                        posts.add(post);
                    }
                    parent.updateAdapter(posts);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
