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

import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.User;

import static android.content.ContentValues.TAG;

public class getAllBooks {
    private View_All_Books_Activity parent;
    private DatabaseReference mDatabase;
    private Display_Username display;

    public getAllBooks(View_All_Books_Activity parent) {
        this.parent = parent;
        getUserQuery();
    }

    private void getUserQuery() {
        mDatabase = FirebaseDatabase.getInstance().getReference("all-books");
        mDatabase.addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //Log.d(TAG, "*********----->"+snapshot.getValue());
                    Book_Instance book = snapshot.getValue(Book_Instance.class);
                    display = new Display_Username(book);
                    getUsernameOwner(book);
                }
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void getUsernameOwner(Book_Instance book){
        final Book_Instance bk = book;
        String UID = book.getOwner();
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                Log.d(ContentValues.TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        display.setOwner(user.getUserName());
                        getBorrowerUsername(bk);
                    }
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }

    private void getBorrowerUsername(Book_Instance book){
        final Book_Instance bk = book;
        String UID = book.getPossessor();
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange (@NonNull DataSnapshot dataSnapshot){
                Log.d(ContentValues.TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        display.setBorrower(user.getUserName());
                        parent.addBook(display);

                    }
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }
}