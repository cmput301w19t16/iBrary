package ca.rededaniskal.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.view.QuerySpec;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.EntityClasses.BorrowRequest;

/**
 * Used to add a book to a borrow request
 */

public class BookDetailsdb{
    Book_Details_Activity parent;
    BorrowRequestDb requestDb;
    private boolean failed;
    String bookId;
    private myCallbackBool mcbb;
    private boolean bookinuserrequests;

    public BookDetailsdb(Book_Details_Activity bda, String bookid){
        parent = bda;
        bookinuserrequests = false;
        requestDb = new BorrowRequestDb();
        this.bookId = bookid;
    }

    public void  bookInUserRequests(final myCallbackBool mcbb, String bookStr){
        this.mcbb = mcbb;

        //Query requested = requestDb.getReference().orderByChild("bookId").equalTo(this.bookId);
        Query requested = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("bookId").equalTo(bookStr);
        requested.addListenerForSingleValueEvent(queryRequestListener);
    }

    ValueEventListener queryRequestListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String user = requestDb.getUID();
            bookinuserrequests = false;
            for (DataSnapshot d:dataSnapshot.getChildren()){
                BorrowRequest b = d.getValue(BorrowRequest.class);

                if (b.getsenderUID().equals(user)){
                    bookinuserrequests = true;
                }
            }
            mcbb.onCallback(bookinuserrequests);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public boolean getFailed(){return failed;}

    ValueEventListener requestedListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                parent.setTrue();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            databaseError.toException();
        }
    };
}