package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ca.rededaniskal.EntityClasses.BookInstance;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

//Author: Revan
public class Book_Details_Activity extends AppCompatActivity {

    TextView DisplayTitle;
    TextView DisplayAuthor;
    TextView DisplayISBN;
    TextView DisplayOwner;
    TextView DisplayStatus;
    TextView DisplayDescription;

    ImageView BookCover;

    Button GoToForum;
    Button Request_Cancel;

    boolean isRequested; //Auxillary variable for keeping track of where we need to go

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__details_);

        //Set the attributes to their corresponding views
        DisplayTitle = (TextView) findViewById(R.id.DisplayTitle);
        DisplayAuthor = (TextView) findViewById(R.id.DisplayAuthor);
        DisplayISBN = (TextView) findViewById(R.id.DisplayISBN);
        DisplayOwner = (TextView) findViewById(R.id.DisplayOwner) ;
        DisplayStatus = (TextView) findViewById(R.id.DisplayStatus);
        DisplayDescription = (TextView) findViewById(R.id.DisplayDescription);

        BookCover = (ImageView) findViewById(R.id.BookCover);

        GoToForum = (Button) findViewById(R.id.GoToForum); //TODO: GO TO ACTIVITy
        Request_Cancel = (Button) findViewById(R.id.request_cancel);

        //Get what was passed in and display it
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        BookInstance book = (BookInstance) extras.get("book"); //Get the book

        DisplayTitle.setText(book.getTitle());
        DisplayAuthor.setText(book.getAuthor());
        DisplayISBN.setText(book.getISBN());
        DisplayOwner.setText(book.getOwner());
        DisplayStatus.setText(book.getStatus());
        //DisplayDescription.setText(book.get); TODO: Descriptions?

        //TODO: Make this the actual user
        final User globalUser = new User("username", "email", "location");

        //Set appropriate text for the button at the bottom

        if ( isRequested){
            Request_Cancel.setText(R.string.cancel_request);
            isRequested = true;
        }else{
            Request_Cancel.setText(R.string.request_book);
            isRequested = false;
        }

        //Set On-Click listeners

        //TODO: Make this go to forum
        /*
        GoToForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ForumActivity.class);
                intent.putExtra("activity","Edit");
                startActivity(intent);
            }
        });
        */
        /*
        Request_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Case cancel book
                if (isRequested){


                    Request_Cancel.setText(R.string.request_book);
                    isRequested = false;

                }else{
                    //Case Request book
                    Request_Cancel.setText(R.string.cancel_request);
                    //TODO: database stuff

                    Request request = new Request(globalUser.getUserName(), ,"borrow" );

                    isRequested = true;

                }
            }
        });
        */
    }
}
