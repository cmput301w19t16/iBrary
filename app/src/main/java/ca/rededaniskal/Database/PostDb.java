package ca.rededaniskal.Database;



import com.google.firebase.database.DatabaseReference;

public class PostDb extends Entity_Database {

    public PostDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FEED.reference());
    }
}
