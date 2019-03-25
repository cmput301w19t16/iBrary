package ca.rededaniskal.Database;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

abstract class Entity_Database {

    FirebaseDatabase db;
    FirebaseAuth mauth;
    boolean exists;
    String FINISHED;


    Entity_Database() {

        this.db = FirebaseDatabase.getInstance();
        this.mauth = FirebaseAuth.getInstance();
    }






    public String getUID() {
        return mauth.getUid();

    }

    abstract public DatabaseReference getReference() ;



    public boolean checkExists(DatabaseReference reference) {


        reference.addListenerForSingleValueEvent(existence);
        while (FINISHED == null) {
        }
        FINISHED = null;

        return exists;


    }

    ValueEventListener existence = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                returnTrue();
            } else returnFalse();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void returnTrue() {
        this.exists = true;
        this.FINISHED = "";
    }

    private void returnFalse() {
        this.exists = false;
        this.FINISHED = "";
    }





}



