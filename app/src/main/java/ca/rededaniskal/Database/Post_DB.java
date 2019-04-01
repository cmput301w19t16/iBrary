package ca.rededaniskal.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.EntityClasses.Post;

public class Post_DB {
    private Post post;
    private String UID;
    private DatabaseReference mDatabase;

    public Post_DB(Post post) {
        this.post = post;
    }

    public void addPostToUserFeed(){
        mDatabase = FirebaseDatabase.getInstance().getReference("home-feed").child(UID);
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(post);
    }

}
