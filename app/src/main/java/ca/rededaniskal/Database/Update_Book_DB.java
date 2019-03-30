package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;

public class Update_Book_DB {
    private BorrowRequest request;
    private Book_Instance book;
    private DatabaseReference mDatabase;
    private String book_instances_key;
    private String all_books_key;
    private boolean isRequested;
    private boolean isAccepted;
    private boolean isBorrowed;
    private String owner;
    private String isbn;

    public Update_Book_DB(Book_Instance book) {
        this.book = book;
        this.isRequested = false;
        this.isAccepted = false;
        this.isBorrowed = false;

        bookStatus();
    }

    public Update_Book_DB(String owner, String isbn){
        this.owner = owner;
        this.isbn = isbn;
        readBook();
    }



    public void readBook(){
        Query query = FirebaseDatabase.getInstance().getReference("all-books")
                .orderByChild("isbn")
                .equalTo(isbn);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        book = snapshot.getValue(Book_Instance.class);
                        if(book.getOwner().equals(owner)) {
                            bookStatus();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void getBookInstance(){
        Query query = FirebaseDatabase.getInstance().getReference("book-instances")
                .child(book.getOwner())
                .orderByChild("isbn")
                .equalTo(book.getISBN());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Book_Instance book_instance = snapshot.getValue(Book_Instance.class);
                        book_instances_key = snapshot.getKey();
                        updateBookInstance();
                    }

                }
                getAllBooks();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getAllBooks(){
        Query query = FirebaseDatabase.getInstance().getReference("all-books")
                .orderByChild("isbn")
                .equalTo(book.getISBN());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Book_Instance book_instance = snapshot.getValue(Book_Instance.class);
                        all_books_key = snapshot.getKey();
                        updateAllBooks();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void updateBookInstance(){
        mDatabase = FirebaseDatabase.getInstance().getReference("book-instances");
        mDatabase.child(book.getOwner())
                .child(book_instances_key)
                .setValue(book);

    }


    public void bookStatus(){
        Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("isbn")
                .equalTo(book.getISBN());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BorrowRequest borrowRequest = snapshot.getValue(BorrowRequest.class);
                        if (book.getOwner().equals(borrowRequest.getrecipientUID())){
                            if(borrowRequest.getStatus().equals("Pending")){
                                isRequested = true;
                            }else if(borrowRequest.getStatus().equals("Accepted")){
                                isAccepted = true;
                            }else if(borrowRequest.getStatus().equals("Borrowed")){
                                isBorrowed = true;
                            }
                        }
                    }
                }
                updateBookStatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void updateBookStatus(){
        if(isRequested){
            book.setStatus("Requested");
        }
        if(isAccepted){
            book.setStatus("Accepted");
        }
        if(isBorrowed){
            book.setStatus("Borrowed");
        }
        if(!isBorrowed && !isAccepted && !isRequested){
            book.setStatus("Available");
        }
        getBookInstance();
    }

    public void updateAllBooks(){
        mDatabase = FirebaseDatabase.getInstance().getReference("all-books");
        mDatabase.child(all_books_key)
                .setValue(book);
    }
}
