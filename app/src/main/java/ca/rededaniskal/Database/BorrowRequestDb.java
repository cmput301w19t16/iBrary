package ca.rededaniskal.Database;


import com.google.firebase.database.DatabaseReference;

public class BorrowRequestDb extends Entity_Database {
    public BorrowRequestDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.BOOKREQUEST.reference());
    }
}
