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
import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.BusinessLogic.myCallbackExchange;
import ca.rededaniskal.BusinessLogic.myCallbackExchangeList;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.User;

/**
 * Used to add a book exchange to the database
 */

public class BookExchangeDb{
    DatabaseReference mDatabase;

    public BookExchangeDb(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getBookExchange(String exId, final myCallbackExchange mcbbe){
        Query query = mDatabase.child("Exchanges/" + exId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mcbbe.onCallback(dataSnapshot.getValue(Exchange.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });

    }

    public void getUsersBorrowedBooks(String uid, final myCallbackExchangeList mcbe){
        Query query = mDatabase.child("Exchanges").orderByChild("borrower").equalTo(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<Exchange> brl = new ArrayList<>();
                    for (DataSnapshot snap : dataSnapshot.getChildren()){
                        brl.add(snap.getValue(Exchange.class));
                    }
                    mcbe.onCallback(brl);
                }
                else{
                    mcbe.onCallback(new ArrayList<Exchange>());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }
}