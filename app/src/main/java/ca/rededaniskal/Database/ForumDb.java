package ca.rededaniskal.Database;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.EntityClasses.Text_Post;

public class ForumDb extends Entity_Database {

    public ForumDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FORUM.reference());
    }

    public void addThread(List childThreads, String ParentID){



    }

    public void addParentThread(){

    }





}
