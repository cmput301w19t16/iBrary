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

import java.util.ArrayList;

import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.User;

import static android.content.ContentValues.TAG;

public class getAllBooks {
    private View_All_Books_Activity parent;
    private DatabaseReference mDatabase;
    private Display_Username display;
    private ArrayList<Display_Username> book_list;

    public getAllBooks(View_All_Books_Activity parent) {
        Log.d(TAG, "*********----->AllBooks");
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
            Log.d(TAG, "*********----->All Books Data change");
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                book_list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Book_Instance book = snapshot.getValue(Book_Instance.class);
                    Log.d(TAG, "*********----->Got this book: " + book.getTitle());
                    display = new Display_Username(book);
                    book_list.add(display);
                }
                getUsernameOwner();
            }
        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private void getUsernameOwner() {
        for (int i = 0; i < book_list.size(); i++) {
            final int j = i;
            final Display_Username dis = book_list.get(i);
            final Book_Instance bk = dis.getBook();
            String UID = bk.getOwner();
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
                            dis.setOwner(user.getUserName());
                            book_list.set(j, dis);
                            getBorrowerUsername();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void getBorrowerUsername() {
        for (int i = 0; i < book_list.size(); i++) {
            final int j = i;
            final Display_Username dis = book_list.get(i);
            final Book_Instance bk = dis.getBook();
            String UID = bk.getPossessor();
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
                            dis.setBorrower(user.getUserName());
                            book_list.set(j, dis);
                        }
                        parent.addBook(book_list);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}