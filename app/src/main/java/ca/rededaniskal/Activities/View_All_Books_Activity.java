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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.Filter_My_Books_Logic;

import ca.rededaniskal.Database.BookInstanceDb;

import ca.rededaniskal.Database.getAllBooks;

import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.R;



public class View_All_Books_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Display_Username> BL;

    private String ISBN;
    private ArrayList<Integer> AvOrRe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__books_);

        AvOrRe = new ArrayList<>();
        AvOrRe.add(1);
        AvOrRe.add(0);


        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            Master_Book mb = (Master_Book) intent.getSerializableExtra("master_book"); //Get the book
            ISBN = mb.getISBN();

        } else {
            ISBN = "";
        }

        getAllBooks db = new getAllBooks(this, ISBN);


        BL = new ArrayList<>(); //Initiatize books to be displayed

        //Set the card views
        recyclerView = (RecyclerView) findViewById(R.id.DisplayBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();




    }
    public void addBook (ArrayList < Display_Username > book_List) {


        ArrayList<Display_Username> book_list = new Filter_My_Books_Logic(AvOrRe, book_List).newBooks();
        bookAdapter = new BookAdapter(this, book_list);

        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }
}
