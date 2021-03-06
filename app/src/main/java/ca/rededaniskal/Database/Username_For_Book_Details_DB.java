package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.User;

/**
 * Gets the username of the owner of a book and also the borrower of the book
 */

public class Username_For_Book_Details_DB {
    private Book_Details_Activity parent;
    private Book_Instance book;

    public Username_For_Book_Details_DB(Book_Details_Activity parent, Book_Instance book) {
        this.parent = parent;
        this.book = book;
        getUsernameOwner();
        getUsernameBorrower();
    }

    private void getUsernameOwner() {
        String UID = book.getOwner();
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(ContentValues.TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        String username = user.getUserName();
                        parent.setUsername(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getUsernameBorrower() {
        String UID = book.getPossessor();
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("uid")
                .equalTo(UID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(ContentValues.TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        String username = user.getUserName();
                        parent.setUsernameBorrower(username);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
