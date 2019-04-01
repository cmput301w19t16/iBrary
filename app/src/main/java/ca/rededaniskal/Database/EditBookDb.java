package ca.rededaniskal.Database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.EntityClasses.Book_Instance;

public class EditBookDb {
    BookInstanceDb bookInstanceDb;



    public EditBookDb() {
        bookInstanceDb = new BookInstanceDb();


    }

    public boolean EditBookData(Book_Instance bookInstance) throws NullPointerException{

        return bookInstanceDb.currentUserBooklist()
                .child(bookInstance.getBookID())
                .setValue(bookInstance)
                .isSuccessful();
    }


    public void DeleteBook(String node){
        bookInstanceDb.currentUserBooklist()
                .child(node)
                .removeValue();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("all-books")
                .child(node);
        mDatabase.removeValue();

    }
}