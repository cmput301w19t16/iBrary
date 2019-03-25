package ca.rededaniskal.Database;

import android.app.Activity;

import com.google.firebase.database.DatabaseReference;

public class ForumDb extends Entity_Database {

    public ForumDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FORUM.reference());
    }
}
