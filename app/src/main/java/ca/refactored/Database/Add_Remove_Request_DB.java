package ca.refactored.Database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.refactored.EntityClasses.Book_Instance;
import ca.refactored.EntityClasses.BorrowRequest;

import static android.support.constraint.Constraints.TAG;

public class Add_Remove_Request_DB {


    private BorrowRequest request;
    private DatabaseReference mDatabase;
    private String key;
    private Book_Instance book;
    private String sender_UID;


    public Add_Remove_Request_DB(BorrowRequest request) {
        Log.d(TAG, "*********------> Add_Remove_Request_DB");
        this.request = request;
        Log.d(TAG, "*********------> Request sender UID: " + request.getsenderUID());
        createRequest();

    }

    public Add_Remove_Request_DB(Book_Instance book, String sender_UID){
        this.sender_UID = sender_UID;
        this.book = book;

        Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("isbn")
                .equalTo(book.getISBN());
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ContentValues.TAG, "*********----->onDataChange: Add_Remove_Request_DB");
            if (dataSnapshot.exists()) {
                Log.d(ContentValues.TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    BorrowRequest req = snapshot.getValue(BorrowRequest.class);
                    if(req.getsenderUID().equals(sender_UID)) {
                        key = snapshot.getKey();
                    }
                }

                deleteRequest();
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };


    private void createRequest(){
        Log.d(TAG, "*********------> createRequest");
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        String k = mDatabase.push().getKey();
        mDatabase.child(k).setValue(request);
    }

    private void deleteRequest(){
        Log.d(TAG, "*********------> deleteRequest");
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        mDatabase.child(key).removeValue();
    }


}
