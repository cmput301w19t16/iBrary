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
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Display_Post;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.User;

import static android.support.constraint.Constraints.TAG;

public class Write_Post_DB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UID;
    private ArrayList<String> followers;
    private myCallbackStringList mcbuid;
    private Follow_DB fdb;
    private DatabaseReference mDatabase;
    private Post post;
    private ArrayList<Display_Post> posts;
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
        mcbuid = new myCallbackStringList() {
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
        Log.d(ContentValues.TAG, "*********----->In home-feed/" + UID);
//        Query query = FirebaseDatabase.getInstance().getReference("home-feed/" + UID);
        Query query = FirebaseDatabase.getInstance().getReference("home-feed/").child(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Post pos = snapshot.getValue(Post.class);
                        Display_Post display = new Display_Post(pos);
                        posts.add(display);

                    }

                    getBookName();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getBookName() {
        for (int i = 0; i < posts.size(); i++) {
            final Display_Post display = posts.get(i);
            final int j = i;
            String p = posts.get(i).getPost().getISBN();
            Log.d(ContentValues.TAG, "*********----->p "+p);
            Query query = FirebaseDatabase.getInstance().getReference("master-books")
                    .child(p);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(ContentValues.TAG, "*********----->onDataChange");
                    if (dataSnapshot.exists()) {
                        Log.d(ContentValues.TAG, "*********----->exists");

                        Master_Book book = dataSnapshot.getValue(Master_Book.class);
                        assert book != null;
                        display.setTitle(book.getTitle());
                        posts.set(j, display);
                    }
                    getBorrowerUsername();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void getBorrowerUsername() {
        Log.d(TAG, "**************---> In getBorrowerUsername "+posts);
        for (int i = 0; i < posts.size(); i++) {
            final Display_Post display = posts.get(i);
            final int j = i;
            String p = posts.get(i).getPost().getUid();
            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("uid")
                    .equalTo(p);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(ContentValues.TAG, "*********----->onDataChange");
                    if (dataSnapshot.exists()) {
                        Log.d(ContentValues.TAG, "*********----->exists");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            User user = snapshot.getValue(User.class);
                            display.setPoster(user.getUserName());
                            Log.d(ContentValues.TAG, "*********----->Post text"+display.getPost().getText());
                            posts.set(j, display);
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
}
