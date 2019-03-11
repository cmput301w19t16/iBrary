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

        readBookDB db = new readBookDB();

        BL = new Book_List();
        BL.addBook(new Book_Instance("Title", "Me", "1111111111", "R", "you", "good", "a"));

        recyclerView = (RecyclerView) findViewById(R.id.ViewBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }

    public void loadBook(Book_Instance book_instance){
        this.BL.addBook(book_instance);
    }


    private class readBookDB{
        private DatabaseReference mdatabase;
        private String TAG= "ViewBORROWED";


        public readBookDB(){
            getBookInstance();
        }


        private void getBookInstance(){
//            Query query = FirebaseDatabase.getInstance().getReference("book-instances")
//                    .child("book-instances").child("all-books");
            mdatabase = FirebaseDatabase.getInstance().getReference("book-instances");
            mdatabase.addListenerForSingleValueEvent(valueEventListener);
        }


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "**************---> In OnDataChange");
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Book_Instance book_instance = snapshot.getValue(Book_Instance.class);
                        Log.d(TAG, "**************--->"+book_instance.getOwner());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    /*public Book_List Load_books(Map<String, Map<String, String>> map){
       this.BL = new Book_List();
        for (Map<String,String> m : map.values()){
            this.BL
                    .addBook(
                            new Book_Instance(m.get("title"),
                                    m.get("author"),
                                    m.get("isbn"),
                                    m.get("owner"),
                                    m.get("possessor"),
                                    m.get("condition"),
                                    m.get("status"))
                    );
            if (this.BL.size()==0){
                try{
                    throw new Exception("The books WERENT ADDED!!!!!!!!!!!! at VIEWBoorowedbooks");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }


        }

        return this.BL;
    }
*/
}