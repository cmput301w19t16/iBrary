package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.User;

import static android.support.constraint.Constraints.TAG;

public class ReadBookDB{
    private View_Borrowed_Requested_Activity parent;
    private BookInstanceDb instanceDb;
    private Display_Username display;


    public ReadBookDB(View_Borrowed_Requested_Activity p){
        parent = p;
        instanceDb = new BookInstanceDb();
        update();
    }


    public void update(){
       instanceDb.currentUserBooklist().addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.d(TAG, "**************---> In OnDataChange");
            if (dataSnapshot.exists()){
                ArrayList<Display_Username> book_list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Book_Instance book_instance = snapshot.getValue(Book_Instance.class);
                    Log.d(TAG, "**************--->"+book_instance.getOwner());
                    display = new Display_Username(book_instance);
                    getUsernameOwner(book_instance);

                }
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
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
                        //parent.updateBookView(display);

                    }
                }
            }

            @Override
            public void onCancelled (@NonNull DatabaseError databaseError){

            }
        });
    }
}
