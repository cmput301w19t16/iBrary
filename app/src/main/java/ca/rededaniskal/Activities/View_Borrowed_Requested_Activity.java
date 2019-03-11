/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View all your requested and borrowed books
 *
 * ISSUES:
 * Needs DB support
 *
 */
package ca.rededaniskal.Activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import ca.rededaniskal.BusinessLogic.BookAdapter;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.R;

//Author: Revan, Skye
public class View_Borrowed_Requested_Activity extends AppCompatActivity {


    private Book_List BL;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private Switch toggleRequested;
    private Switch toggleBorrowed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__borrowed__requested_);




        recyclerView = (RecyclerView) findViewById(R.id.ViewBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        readBookDB db = new readBookDB();
        db.update();



    }
    public void updateBookView(Book_List book_list){
        bookAdapter = new BookAdapter(this, book_list);


        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();



    }





    private class readBookDB{
        private DatabaseReference mdatabase;
        String TAG;




        public readBookDB(){

            update();

        }


        private void update(){
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

            mdatabase = FirebaseDatabase.getInstance().getReference("book-instances").child(user);
            mdatabase.addListenerForSingleValueEvent(valueEventListener);
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
                    updateBookView(book_list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };



    }

}

