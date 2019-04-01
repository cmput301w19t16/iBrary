package ca.rededaniskal.Database;


import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.BusinessLogic.myCallbackBookRequest;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;

/**
 * Used to get borrow request from the database
 */

public class BorrowRequestDb extends Entity_Database {
    public BorrowRequestDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.BOOKREQUEST.reference());
    }

    public void getBookRequest(String borrowID, final myCallbackBookRequest mcb) {
        Query query = getReference().child(borrowID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                BorrowRequest borrowRequest;// = new BorrowRequest();
                if (dataSnapshot.exists()) {
                    borrowRequest = dataSnapshot.getValue(BorrowRequest.class);
                    mcb.onCallback(borrowRequest);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
