package ca.rededaniskal.Database;



import com.google.firebase.database.DatabaseReference;

public class FriendRequestDb extends Entity_Database {

    public FriendRequestDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FRIENDREQUEST.reference());
    }
}
