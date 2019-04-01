package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.View_Pending_Exchanges_Activity;
import ca.rededaniskal.EntityClasses.Book;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.Exchange;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Returns the owner and borrower in the exchange process
 */

public class Read_Exchange_DB {
    private View_Pending_Exchanges_Activity parent;
    private ArrayList<Exchange> exchanges;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UID;

    public Read_Exchange_DB(View_Pending_Exchanges_Activity parent) {
        this.parent = parent;
        exchanges = new ArrayList<>();

    }

    public void getUserExchanges(){
        getCurrentUID();
    }

    private void getCurrentUID() {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            readExchangesOwner();
        }else{
            parent.returnToLogin();
        }
    }

    private void readExchangesOwner(){
        Query query = FirebaseDatabase.getInstance().getReference("Exchanges")
                .orderByChild("owner")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    exchanges = new ArrayList<>();
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Exchange exchange = snapshot.getValue(Exchange.class);
                        exchanges.add(exchange);
                    }
                }
                readExchangesBorrower();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readExchangesBorrower(){
        Query query = FirebaseDatabase.getInstance().getReference("Exchanges")
                .orderByChild("borrower")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    exchanges = new ArrayList<>();
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Exchange exchange = snapshot.getValue(Exchange.class);
                        exchanges.add(exchange);
                    }
                }
                parent.updateAdapter(exchanges);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
