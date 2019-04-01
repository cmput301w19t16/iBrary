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
import ca.rededaniskal.EntityClasses.Exchange;

/**
 * Updates the status of the books in the database based on what the request type is
 */

public class Update_Book_DB {
    private BorrowRequest request;
    private Book_Instance book;
    private DatabaseReference mDatabase;
    private boolean isRequested;
    private boolean isAccepted;
    private boolean isBorrowed;
    private String owner;
    private String isbn;
    private String book_key;
    private String possesor;

    public Update_Book_DB(Book_Instance book) {
        this.book = book;
        this.isRequested = false;
        this.isAccepted = false;
        this.isBorrowed = false;
        this.book_key = book.getBookID();

        bookStatusByRequest();
    }

    public Update_Book_DB(String owner, String possesor, String isbn){
        this.possesor = possesor;
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
                            book.setPossessor(possesor);
                            bookStatusByRequest();
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
                        Book_Instance book = snapshot.getValue(Book_Instance.class);
                        book_key = snapshot.getKey();
                        updateBookInstance();
                        updateAllBooks();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    public void bookStatusByRequest(){
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
                            if(borrowRequest.getStatus().equals("Pending")) {
                                isRequested = true;
                            }
                        }
                    }
                }
                bookStatusByExchange();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void bookStatusByExchange(){
        Query query = FirebaseDatabase.getInstance().getReference("Exchanges")
                .orderByChild("isbn")
                .equalTo(book.getISBN());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Exchange exchange = snapshot.getValue(Exchange.class);
                        if (book.getOwner().equals(exchange.getOwner())){
                            if(exchange.isOwnerScanend() && !exchange.isReturning()) {
                                isBorrowed = true;
                            }else if (exchange.isBorrowedScanned() && exchange.isReturning()){
                                isAccepted = false;
                                isBorrowed = false;
                                isRequested = false;
                            }else{
                                isAccepted = true;
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
        if(!book.getOwner().equals(book.getPossessor())){
            book.setStatus("Borrowed");
        }
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
        mDatabase.child(book_key)
                .setValue(book);
    }

    public void updateBookInstance(){
        mDatabase = FirebaseDatabase.getInstance().getReference("book-instances");
        mDatabase.child(book.getOwner())
                .child(book_key)
                .setValue(book);

    }
}
