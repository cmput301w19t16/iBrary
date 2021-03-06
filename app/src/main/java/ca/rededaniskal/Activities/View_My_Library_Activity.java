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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.Filter_My_Books_Logic;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.R;
import ca.rededaniskal.Database.ReadMyBookDB;

/**
 * This activity displays all the books the user owns using a recycler view. The user is allowed
 * to click on a book to see it's details
 */

public class View_My_Library_Activity extends AppCompatActivity {

    private static final String TAG = "View_My_Library_Activity";
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private FloatingActionButton fab;
    private Button filter;

    String[] filterOptions;
    boolean[] selectedOptions;
    ArrayList<Integer> chosenOptions = new ArrayList<>();
    private ArrayList<Display_Username> BL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        BL = new ArrayList<Display_Username>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__my__library_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Library");

        filterOptions = getResources().getStringArray(R.array.filter_my_library);
        selectedOptions = new boolean[filterOptions.length];

        recyclerView = (RecyclerView) findViewById(R.id.DisplayBooks);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();


        filter = findViewById(R.id.filter);
        fab = findViewById(R.id.addBookToLibrary);

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

        final View_My_Library_Activity thisactivity = this;

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
                       new ReadMyBookDB(thisactivity).update();

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
        Log.d(TAG, "**************---> ABout to call ReadMyBookDB");
        ReadMyBookDB db = new ReadMyBookDB(this);
    }

    //Update the View
    public void updateBookView(ArrayList<Display_Username> book_list){

        //uses filter book logic to allow users to filter books by status
        if (chosenOptions.size()!=0){
            Filter_My_Books_Logic filter = new Filter_My_Books_Logic(chosenOptions, book_list);
            bookAdapter = new BookAdapter(this, filter.newBooks());
        }else {
            bookAdapter = new BookAdapter(this, book_list);
        }

        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }


    //Class responsible for reading DB

}
