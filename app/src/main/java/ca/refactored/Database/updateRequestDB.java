package ca.refactored.Database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.refactored.EntityClasses.BorrowRequest;

import static android.support.constraint.Constraints.TAG;

public class updateRequestDB{
    private BorrowRequest request;
    private DatabaseReference mDatabase;
    private String key;
    private boolean delete;

    public updateRequestDB(BorrowRequest request) {
        Log.d(TAG, "*********------> updateRequestDB");

        this.request = request;
        Log.d(TAG, "*********------> Request sender UID: " + request.getsenderUID());
        Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("isbn")
                .equalTo(request.getIsbn());

        query.addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ContentValues.TAG, "*********----->onDataChange: Adapter");
            if (dataSnapshot.exists()) {
                Log.d(ContentValues.TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    BorrowRequest req = snapshot.getValue(BorrowRequest.class);
                    if(req.getsenderUID().equals(request.getsenderUID())) {
                        key = snapshot.getKey();
                    }
                }

                updateRequest();

            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void updateRequest(){
        if(request.getStatus().equals("Accepted")){
            delete = false;
        }else{
            delete = true;
        }
        Log.d(TAG, "*********------> I JUST DONT KEY: "+key);
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        if(delete){
            mDatabase.child(key).removeValue();
        }else{
            mDatabase.child(key).setValue(request);
        }
    }





}