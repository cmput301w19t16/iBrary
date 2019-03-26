/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View all Books
 * Temporary until search is implemented
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

public class View_All_Books_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private Book_List BL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__books_);

        Intent intent = getIntent();

        if (intent.getExtras() != null ){
            Master_Book mb = (Master_Book) intent.getSerializableExtra("master_book"); //Get the book
            //TODO: get related books for the database
        }

        BL = new Book_List(); //Initiatize books to be displayed

        //Set the card views
        recyclerView = (RecyclerView) findViewById(R.id.DisplayBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        getAllBooks db = new getAllBooks();
    }


    private class getAllBooks{
        DatabaseReference mDatabase;

        public getAllBooks() {
            getUserQuery();
        }

        private void getUserQuery(){
            mDatabase = FirebaseDatabase.getInstance().getReference("all_books");
            mDatabase.addListenerForSingleValueEvent(valueEventListener);
        }


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BL.clear();
                Log.d(TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.d(TAG, "*********----->"+snapshot.getValue());
                        Book_Instance book = snapshot.getValue(Book_Instance.class);
                        BL.addBook(book);
                    }
                    bookAdapter.notifyDataSetChanged();
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }
}
