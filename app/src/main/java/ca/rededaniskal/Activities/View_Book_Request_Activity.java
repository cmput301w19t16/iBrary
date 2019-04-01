package ca.rededaniskal.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import ca.rededaniskal.BusinessLogic.LoadImage;
import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.Database.Write_Request_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

public class View_Book_Request_Activity extends AppCompatActivity {

    private TextView nameField;
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
    private boolean returning;
    private TableRow userPicRow, userNameRow,bookCoverRow,titleRow, authorRow;
    private ImageView bookPic;

    private ImageView profilePic;

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

        nameField = findViewById(R.id.username);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("View Request");

        title = findViewById(R.id.Title);
        author = findViewById(R.id.Author);
        denyButton = findViewById(R.id.DenyButton);
        confirmButton = findViewById(R.id.ConfirmButton);

        titleRow = findViewById(R.id.titleRow);
        authorRow = findViewById(R.id.authorRow);
        bookCoverRow = findViewById(R.id.bookCoverRow);
        View.OnClickListener bookListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBook();
            }
        };
        titleRow.setOnClickListener(bookListener);
        authorRow.setOnClickListener(bookListener);
        bookCoverRow.setOnClickListener(bookListener);
        bookPic = findViewById(R.id.BookCover);


        userNameRow = findViewById(R.id.userNameRow);
        userPicRow = findViewById(R.id.userPicRow);
        View.OnClickListener userListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewUser();
            }
        };

        userNameRow.setOnClickListener(userListener);
        userPicRow.setOnClickListener(userListener);



        myCallbackBookInstance mcbbi = new myCallbackBookInstance() {
            @Override
            public void onCallback(Book_Instance book_instance) {
                book = book_instance;
                fillBookField();
                if(book.getCover() != null || book.getCover() != ""){
                    LoadImage loader = new LoadImage(bookPic);
                    loader.execute(book.getCover());
                }
            }
        };
        bidb.getBookInstance(currentUID, bookID, mcbbi);
        askField = findViewById(R.id.askedField);
        askField.setText("Asked to borrow your copy of ");


        //Set the Picture
        profilePic = findViewById(R.id.profilePic);
        String uid = br.getsenderUID();
        Users_DB usersDb = new Users_DB();

        myCallbackUser myCallbackUser = new myCallbackUser() {
            @Override
            public void onCallback(User user) {
                String urlProfilePic = user.getProfilePic();
                if(urlProfilePic != null){
                    LoadImage loader = new LoadImage(profilePic);
                    loader.execute(urlProfilePic);
                }
            }
        };

        usersDb.getUser(uid, myCallbackUser);





        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptRequest();
            }
        });


        denyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denyRequest();
            }
        });
    }

    private void fillUserField(){
        nameField.setText(user.getUserName());
    }

    private void fillBookField(){
        title.setText(book.getTitle());
        author.setText("Author: " + book.getAuthor());
    }

    //TODO
    private void acceptRequest(){
        br.setStatus("Accepted");
        Intent intent = new Intent(this, Establish_Exchange_Details_Activity.class);
        intent.putExtra("BorrowRequestObject", br);
        intent.putExtra("Returning", false);

        startActivity(intent);
        this.finish();
    }

    //TODO
    private void denyRequest(){
        br.setStatus("Denied");
        Write_Request_DB db = new Write_Request_DB(br, true);
        this.finish();
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
