package ca.rededaniskal.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.myCallbackUidList;
import ca.rededaniskal.EntityClasses.Post;

public class Write_Post_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UID;
    private ArrayList<String> followers;
    private myCallbackUidList mcbuid;
    private Follow_DB fdb;
    private DatabaseReference mDatabase;
    private Post post;

    public Write_Post_DB(Post post) {
        this.post = post;
    }

    public void addPostToFollowersFeed(){
        getCurrentUID();
    }

    private void getCurrentUID() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            getFollowers();

        }
    }

    public void getFollowers(){
        fdb.getFollowers(UID, mcbuid);
        mcbuid = new myCallbackUidList() {
            @Override
            public void onCallback(ArrayList<String> uidList) {
                followers = uidList;
                addPostToFeeds();
            }
        };
    }

    public void addPostToFeeds(){
        for(int i = 0; i < followers.size(); i++) {
            String follower = followers.get(i);
            mDatabase = FirebaseDatabase.getInstance().getReference("home-feeds")
                    .child(follower);
            String key = mDatabase.push().getKey();
            mDatabase.child(key).setValue(post);
        }
    }






}
