package ca.rededaniskal.Database;
/*author Skye*/

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book_Instance;

public class AddBookDb {
    FirebaseDatabase db;
    DatabaseReference bookRef;
    String success;

    public AddBookDb() {
        this.db = FirebaseDatabase.getInstance();
        this.bookRef = db.getReference().child("book-instances");

    }

    public String addBookToDatabase(Book_Instance bookInstance) throws NullPointerException{


        success =bookRef.push().getKey();



        if (bookRef.child(success).setValue(bookInstance).isSuccessful()){
            bookInstance.setBookID(success);
            bookRef.child(success).setValue(bookInstance);
        }
        else {
            success = "";
        }




        return success;
    }

}
