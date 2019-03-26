package ca.refactored.Database;



import com.google.firebase.database.DatabaseReference;

public class BookExchangeDb extends Entity_Database {
    public BookExchangeDb() {
        super();
    }


    @Override
    public DatabaseReference getReference() {
        return db.getReference();
    }


}
