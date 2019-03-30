package ca.rededaniskal.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.Exchange;

public class Write_Exchange_DB {
    private DatabaseReference mDatabase;
    private Exchange exchange;

    public Write_Exchange_DB(){}

    public void addExchange(Exchange exchange){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(exchange);
    }

    public void removeExchange(String key){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        mDatabase.child(key).removeValue();
    }

    public void updateExchange(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Exchanges");
        String key = mDatabase.push().getKey();
        mDatabase.child(key).setValue(exchange);
    }
}
