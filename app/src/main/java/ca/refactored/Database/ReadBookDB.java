package ca.refactored.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ca.refactored.EntityClasses.Book_Instance;
import ca.refactored.EntityClasses.Book_List;
import ca.refactored.Activities.View_Borrowed_Requested_Activity;

import static android.support.constraint.Constraints.TAG;

public class ReadBookDB{
    private View_Borrowed_Requested_Activity parent;
    private BookInstanceDb instanceDb;


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
                Book_List book_list = new Book_List();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Book_Instance book_instance = snapshot.getValue(Book_Instance.class);
                    Log.d(TAG, "**************--->"+book_instance.getOwner());
                    book_list.addBook(book_instance);

                }
                parent.updateBookView(book_list);
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}
