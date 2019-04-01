package ca.rededaniskal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.Filter_My_Books_Logic;
import ca.rededaniskal.Database.ReadMyBookDB;
import ca.rededaniskal.Database.ReadUserBookDB;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

public class View_Users_Library_Activity extends AppCompatActivity {

    RecyclerView viewBooks;
    BookAdapter bookAdapter;
    User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__users__library_);

        //Get the User
        user = (User) getIntent().getSerializableExtra("user");

        //Set the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(user.getUserName().concat("'s Library"));

        //Set the recycler view
        viewBooks = findViewById(R.id.DisplayBooks);
        viewBooks.setHasFixedSize(true);
        viewBooks.setLayoutManager(new LinearLayoutManager(this));

        ReadUserBookDB db = new ReadUserBookDB(this, user.getUID());

    }

    public void updateBookView(ArrayList<Display_Username> book_list){

        bookAdapter = new BookAdapter(this, book_list);
        viewBooks.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();
    }

}
