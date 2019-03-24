package ca.rededaniskal.Database;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.Book_Instance;

abstract class iBrary_Database {
    boolean exists;
    String FINISHED;
    FirebaseDatabase db;
    FirebaseAuth mauth;


    iBrary_Database() {
        this.db = FirebaseDatabase.getInstance();
        this.mauth = FirebaseAuth.getInstance();
    }

    public boolean isExists() {
        return exists;
    }

    public DatabaseReference getReference(References reference) {
        if (reference == References.MASTERBOOK) {
            return db.getReference("master-books");

        } else if (reference == References.BOOKINSTANCE) {
            return db.getReference("book-instances");
        } else if (reference == References.FEED) {
            return db.getReference("home-feed");
        } else if (reference == References.BOOKREQUEST) {

            return db.getReference("BorrowRequests");
        } else if (reference == References.USER) {
            return db.getReference("Users");
        } else if (reference == References.FRIENDREQUEST) {

            return db.getReference("FriendRequests");
        }else return db.getReference();

    }


    public String getUID() {
        return mauth.getUid();

    }

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


    public abstract void update();


}



