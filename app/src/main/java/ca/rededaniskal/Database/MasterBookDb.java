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










}
