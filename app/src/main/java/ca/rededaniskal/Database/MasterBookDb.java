package ca.rededaniskal.Database;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.Master_Book;


public class MasterBookDb extends Entity_Database {
    DatabaseReference mainRef = db.getReference(References.MASTERBOOK.reference());

    String DONE;


    public MasterBookDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return mainRef;
    }



    public void addMasterBook(Master_Book master_book){
        mainRef.child(master_book.getISBN()).setValue(master_book);
    }






    /*public ArrayList<String> get_all_ISBN(){
        final ArrayList<String> isbns = new ArrayList<>();
        ValueEventListener isbnListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot d: dataSnapshot.getChildren()){
                    String isbn = d.getKey();
                    isbns.add(isbn);
                }
                DONE="";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } ;
        mainRef.addListenerForSingleValueEvent(isbnListener);
        while (DONE==null){}
        DONE=null;
        return isbns;*/



}
