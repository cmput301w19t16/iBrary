package ca.rededaniskal.Database;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

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
