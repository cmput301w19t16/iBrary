package ca.rededaniskal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
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
        followers = 0;

        udb = new Users_DB();
        myCallbackUser mcb = new myCallbackUser() {
            @Override
            public void onCallback(User u) {
                user = u;
                fillUserField();
            }
        };
        udb.getUser(uid, mcb);

        bidb = new BookInstanceDb();
        currentUID = bidb.getUID();

        nameField = findViewById(R.id.userField);
        nameField = findViewById(R.id.username);
        locationField = findViewById(R.id.location);
        followersField = findViewById(R.id.followers);
        userField = findViewById(R.id.userField);

        title = findViewById(R.id.Title);
        author = findViewById(R.id.Author);




        userField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser();
            }
        });

        //author = findViewById(R.id.author);

        myCallbackBookInstance mcbbi = new myCallbackBookInstance() {
            @Override
            public void onCallback(Book_Instance book_instance) {
                book = book_instance;
                fillBookField();
            }
        };
        bidb.getBookInstance(currentUID, bookID, mcbbi);
        //title.setText(currentUID);
        //author.setText(bookID);

        bookField = findViewById(R.id.bookField);
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
        locationField.setText("get the location"); //TODO: put user location into user properly (user.getlocation());
        followersField.setText(Integer.toString(user.getFollowerCount()) + " followers");
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
