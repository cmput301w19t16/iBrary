package ca.rededaniskal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

public class View_Book_Request_Activity extends AppCompatActivity {

    private TextView nameField;
    private TextView locationField;
    private TextView followersField;
    private User user;
    private Users_DB udb;
    private String uid;
    private BorrowRequest br;
    private Button confirmButton;
    private Button denyButton;
    private Button userField;
    private Button bookField;
    private TextView title;
    private TextView author;
    private int followers;
    private TextView askField;
    private Book_Instance book;
    private BookInstanceDb bidb;
    private String bookID;
    private String currentUID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_book_request);

        br = (BorrowRequest) getIntent().getSerializableExtra("request");
        uid = br.getsenderUID();
        bookID = br.getBookId();

        udb = new Users_DB();
        user = udb.getUser(uid);

        bidb = new BookInstanceDb();
        currentUID = bidb.getUID();
        book = bidb.getBookInstance(currentUID, bookID);

        nameField = findViewById(R.id.username);
        locationField = findViewById(R.id.location);
        followersField = findViewById(R.id.followers);
        userField = findViewById(R.id.userField);

        fillUserField();
        userField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser();
            }
        });


        title = findViewById(R.id.title);
        author = findViewById(R.id.title);
        followers = 0;

        fillBookField();
        bookField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBook();
            }
        });



        askField = findViewById(R.id.askedField);
        askField.setText("Asked to borrow your copy of ");

        confirmButton = findViewById(R.id.ConfirmButton);
        confirmButton.setBackgroundColor(getResources().getColor(R.color.acceptGreen, getTheme()));
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
            }
        });

        denyButton = findViewById(R.id.DenyButton);
        denyButton.setBackgroundColor(getResources().getColor(R.color.denyRed, getTheme()));
        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denyRequest();
            }
        });


    }

    private void fillUserField(){
        nameField.setText(user.getUserName());
        locationField.setText(user.getLocation());
        followersField.setText(Integer.toString(followers) + " followers");
    }

    private void fillBookField(){
        title.setText("Title: " + book.getTitle());
        author.setText("Author: " + book.getAuthor());
    }

    //TODO
    private void acceptRequest(){

    }

    //TODO
    private void denyRequest(){

    }

    private void viewBook(){
        Intent intent = new Intent(this, Book_Details_Activity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }

    private void viewUser(){
        Intent intent = new Intent(getApplicationContext(), User_Details_Activity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

}
