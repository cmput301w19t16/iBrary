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

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.myCallbackBRList;
import ca.rededaniskal.BusinessLogic.myCallbackBookRequest;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.EntityClasses.BorrowRequest;

public class Borrow_Req_DB {
    private DatabaseReference mDatabase;

    public Borrow_Req_DB() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getBorrowReq(String reqID, final myCallbackBookRequest mcbr) {

        Query query = mDatabase.child("BorrowRequests/" + reqID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mcbr.onCallback(dataSnapshot.getValue(BorrowRequest.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });

    }

    public void getBooksBorrowRequests(final String bookID, final myCallbackBRList mcbrl) {
        final myCallbackStringList mcbsl = new myCallbackStringList() {
            @Override
            public void onCallback(final ArrayList<String> strList) {
                Query query = mDatabase.child("BorrowRequests");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            ArrayList<BorrowRequest> brl = new ArrayList<>();

                            for (String reqID : strList) {
                                brl.add(dataSnapshot.child(reqID).getValue(BorrowRequest.class));
                            }

                            mcbrl.onCallback(brl);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
                    }
                });
            }
        };
        getBooksBorrowRequestIDS(bookID, mcbsl);
    }

    public void getBooksBorrowRequestIDS(String bookID, final myCallbackStringList mcbsl) {
        Query query = mDatabase.child("BorrowRequests").orderByChild("bookId").equalTo(bookID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> al = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        al.add(snapshot.getKey());
                    }
                    mcbsl.onCallback(al);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });

    }
}