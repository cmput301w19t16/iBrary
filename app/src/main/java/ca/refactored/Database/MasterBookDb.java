package ca.refactored.Database;

import com.google.firebase.database.DatabaseReference;

import ca.refactored.EntityClasses.Master_Book;


public class MasterBookDb extends Entity_Database {
    DatabaseReference mainRef = db.getReference(References.MASTERBOOK.reference());
    private final static String instanceRef = "instances";
    private final static String bookRef = "book";


    public MasterBookDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return mainRef;
    }



    public void addMasterBook(Master_Book master_book){
        mainRef.child(master_book.getISBN()).child(bookRef).setValue(master_book);
    }

    public void addInstance(String ISBN, String bookId){
        mainRef.child(ISBN).child(instanceRef).child(getUID()).child(bookId).setValue(true);
    }
}
