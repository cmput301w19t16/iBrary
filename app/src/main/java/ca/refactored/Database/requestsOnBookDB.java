package ca.refactored.Database;

/*
* Author: Delaney, Nick
* Will either create a borrow request, or read in all borrow requests of the current user
* on the given book instance.
* Parent activity is Book_Details_Activity
* */


import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.refactored.Activities.Book_Details_Activity;
import ca.refactored.EntityClasses.Book_Instance;
import ca.refactored.EntityClasses.BorrowRequest;

import static android.content.ContentValues.TAG;

public class requestsOnBookDB {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference mDatabase;
    private boolean submit;
    private Book_Instance book;
    private boolean failed;
    private Book_Details_Activity parent;
    private String UID;

    /*
    Constructors. If a book instance is passed, then a request is being made on a book.
    Otherwise requests are being read for the current signed in user.
    */

    public requestsOnBookDB(Book_Details_Activity p) {
        parent = p;
        submit = false;
        checkUser();
    }


    public requestsOnBookDB(Book_Instance book, Book_Details_Activity p){
        parent = p;
        submit = true;
        this.book = book;
        checkUser();

    }

    private void checkUser(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {

            UID = user.getUid();

            failed = false;

            if(submit){
                createRequest();
            }else{
                getRequests();
            }

        } else {
            failed = true;
        }
    }


    public boolean getFailed(){return failed;}

    private void createRequest(){
        BorrowRequest req = new BorrowRequest(UID, book.getOwner(), book.getISBN(), book.getBookID());
        submitRequest(req);
    }


    private void submitRequest(BorrowRequest request){
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(request);
    }


    private void getOwnerUID(String uid){
    }


    private void getRequests() {

        Query query2 = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("recipientUID").equalTo(UID);

        query2.addListenerForSingleValueEvent(valueEventListener3);
    }

    ValueEventListener valueEventListener3 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange3");
            parent.listClear();
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                    if (request.getIsbn().equals(parent.getBookISBN())) {
                        Log.d(TAG, "*********----->Books " + request.getIsbn());
                        parent.append(request);
                    }
                }
                parent.append(new BorrowRequest());
                parent.notifyRequest();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}