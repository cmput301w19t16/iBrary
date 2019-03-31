package ca.rededaniskal.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.BusinessLogic.Write_Notification_Logic;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.Exchange;

public class Write_Exchange_DB {
    private DatabaseReference mDatabase;
    private Exchange exchange;

    public Write_Exchange_DB(){}

    public void addExchange(Exchange exchange){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = mDatabase.push().getKey();
        exchange.setExchangeID(key);
        mDatabase.child(key).setValue(exchange);
        if(exchange.isReturning()){
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
    }

    public void updateExchange(Exchange exchange){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = exchange.getExchangeID();
        mDatabase.child(key).setValue(exchange);
    }
}
