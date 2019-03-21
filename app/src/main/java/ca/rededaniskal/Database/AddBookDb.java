package ca.rededaniskal.Database;
/*author Skye*/
//Interacts with the Firebase when a user adds a book to ther library
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;

public class AddBookDb {
    public final String TAG = "AddBookDb";

    FirebaseDatabase db;
    DatabaseReference bookRef;
    DatabaseReference masterRef;
    String success;
    Book_Instance book_instance;

    public AddBookDb() {
        //Creates a new reference to the correct path in the Firebase
        //Book instances are stored under there unique id, under my-books,
        //under unique user Uid, under book-instances.

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.db = FirebaseDatabase.getInstance();
        this.bookRef = db.getReference().child("book-instances")
                .child(user);

    }

    public String addBookToDatabase(Book_Instance bookInstance) throws NullPointerException{
        this.book_instance = bookInstance;


        success =bookRef.push().getKey();
        bookInstance.setBookID(success);
        Log.d(TAG, "***********---->" +bookInstance.getBookID());


        //Stores value
        //TODO: update master-book
        masterRef= FirebaseDatabase.getInstance().getReference().child("master-books").child(bookInstance.getISBN());

        masterRef.addListenerForSingleValueEvent(masterListener);




        if (bookRef.child(success).setValue(bookInstance).isSuccessful()){
            return success;

        }
        else return null;





    }

    ValueEventListener masterListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                updateMaster();
            }
            else{
                addMaster();
            }


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void updateMaster(){
        masterRef.child("instances").child(book_instance.getOwner()).child(book_instance.getBookID()).setValue(true);


    }

    public void addMaster(){
        Master_Book mb = new Master_Book(book_instance.getTitle(), book_instance.getAuthor(), book_instance.getISBN());
        masterRef.child("book").setValue(mb);
        masterRef.child("instances").child(book_instance.getOwner()).child(book_instance.getBookID()).setValue(true);


    }

}
