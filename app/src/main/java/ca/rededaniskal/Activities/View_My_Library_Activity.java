/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View the books you own
 *
 * ISSUES:
 * Needs pretty much everything
 *
 */
package ca.rededaniskal.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.R;



public class View_My_Library_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private FloatingActionButton fab;
    private Button filter;

    String[] filterOptions;
    boolean[] selectedOptions;
    ArrayList<Integer> chosenOptions = new ArrayList<>();
    Book_List BL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BL = new Book_List();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__my__library_);

        filterOptions = getResources().getStringArray(R.array.filter_my_library);
        selectedOptions = new boolean[filterOptions.length];

        recyclerView = (RecyclerView) findViewById(R.id.DisplayBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
        readmyBookDB db = new readmyBookDB();

        filter = findViewById(R.id.filter);
        fab = (FloatingActionButton) findViewById(R.id.addBookToLibrary);

        //Set the on Click listeners
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add a new book
                Intent intent = new Intent(v.getContext(), Add_Book_To_Library_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        //For filtering options
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(View_My_Library_Activity.this);
                builder.setTitle(R.string.search_by);
                builder.setMultiChoiceItems(filterOptions, selectedOptions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            chosenOptions.add(position);
                        }else{
                            chosenOptions.remove((Integer.valueOf(position)));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                       new readmyBookDB().update();

                    }
                });

                builder.setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton(R.string.clear_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < selectedOptions.length; i++) {
                            selectedOptions[i] = false;
                            chosenOptions.clear();
                            //mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });

    }

    //Update the View
    public void updateBookView(Book_List book_list){
        //uses filter book logic to allow users to filter books by status
        if (chosenOptions.size()!=0){
        Filter_My_Books_Logic filter = new Filter_My_Books_Logic(chosenOptions, book_list);
        bookAdapter = new BookAdapter(this, filter.newBooks());
        }
        else {

            bookAdapter = new BookAdapter(this, book_list);
        }

        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }


    //Class responsible for reading DB
    private class readmyBookDB{

        private DatabaseReference mdatabase;
        String TAG;


        public readmyBookDB(){
            update();

        }


        private void update(){
            String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
            mdatabase = FirebaseDatabase.getInstance().getReference("book-instances").child(user).child("my-books");
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
