package ca.rededaniskal;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class DatabaseReturnList {
    private FirebaseDatabase database;
    private DatabaseReference listRef;

    private String refereneceName;
    private String filterby;
    private String filterVal;
    private ArrayList returnList;

    public DatabaseReturnList(String listName,  String filterby, String filterVal) {
        this.refereneceName = listName;
        this.filterby = filterby;
        this.filterVal = filterVal;
        database=FirebaseDatabase.getInstance();
        listRef = database.getReference().child(refereneceName);
        Query listQuery = listRef.child(this.filterby).equalTo(this.filterVal);
        listQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateList((Map<String,Object>)dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void populateList(Map<String, Object> stringObjectMap) {
        this.returnList = new ArrayList();
        for(Map.Entry<String, Object> entry: stringObjectMap.entrySet()){
            this.returnList.add(entry.getValue());
        }

    }

    public ArrayList getReturnList() {
        return returnList;
    }
}
