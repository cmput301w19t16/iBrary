package ca.rededaniskal.Database;


import com.google.firebase.database.DatabaseReference;

import ca.rededaniskal.EntityClasses.Book_Instance;

public class BookInstanceDb extends Entity_Database {
    private DatabaseReference mainRef;

    public BookInstanceDb() {
        super();
        mainRef = db.getReference(References.BOOKINSTANCE.reference());
    }

    @Override
    public DatabaseReference getReference() {
        return mainRef;
    }
    public DatabaseReference currentUserBooklist(){ return mainRef.child(getUID());}



    public String getStorageId() {
        return mainRef.child(getUID()).push().getKey();
    }

    public boolean addBookInstance(Book_Instance book_instance){
        return mainRef.child(getUID())
                .child(book_instance.getBookID())
                .setValue(book_instance)
                .isSuccessful();
    }

    public void viewBookList(){}

    public void viewSingleBook(){}




}
