package ca.rededaniskal.Database;



import com.google.firebase.database.DatabaseReference;

import ca.rededaniskal.EntityClasses.Book_Exchange;

public class BookExchangeDb extends Entity_Database {
    DatabaseReference mainref;
    public BookExchangeDb() {
        super();
        mainref = db.getReference();
    }


    @Override
    public DatabaseReference getReference() {
        return mainref;

    }

    public void addBookExchange(Book_Exchange book_exchange){
        String newkey = mainref.push().getKey();
        mainref.child(newkey).setValue(book_exchange);


    }




}
