package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.BusinessLogic.Write_Notification_Logic;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.EntityClasses.Notification;

public class Write_Exchange_DB {
    private DatabaseReference mDatabase;
    private Exchange exchange;

    public Write_Exchange_DB(){}

    public void addExchange(Exchange exchange){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = mDatabase.push().getKey();
        exchange.setExchangeID(key);
        mDatabase.child(key).setValue(exchange);
        if(!exchange.isReturning()){
            String requestType = "Book Request Accepted";
            Write_Notification_Logic new_notif = new Write_Notification_Logic(exchange.getBorrower(), exchange.getOwner(), key, requestType);
        }else {
            String requestType = "Return_Request";
            Write_Notification_Logic new_notif = new Write_Notification_Logic(exchange.getOwner(), exchange.getBorrower(), key, requestType);
        }
    }

    public void removeExchange(Exchange exchange){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = exchange.getExchangeID();
        mDatabase.child(key).removeValue();
        getNotificationKey(key);
    }

    public void updateExchange(Exchange exchange){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = exchange.getExchangeID();
        mDatabase.child(key).setValue(exchange);
    }

    public void getNotificationKey(String key){
        Log.d(ContentValues.TAG, "*********----->getNotificationKey");
        Query query = FirebaseDatabase.getInstance().getReference("Notifications")
                .orderByChild("request")
                .equalTo(key);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ContentValues.TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(ContentValues.TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Notification notif = snapshot.getValue(Notification.class);
                    String key = snapshot.getKey();
                    removeNotification(key);
                }

            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void removeNotification(String key){
        Log.d(ContentValues.TAG, "*********----->removeNotification");
        mDatabase = FirebaseDatabase.getInstance().getReference("Notifications");
        mDatabase.child(key).removeValue();
    }
}
