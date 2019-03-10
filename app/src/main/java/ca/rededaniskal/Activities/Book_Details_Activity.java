package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.BorrowRequestAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
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
    Button Edit;

    RecyclerView viewRequests;

    BorrowRequestAdapter requestAdapter;

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
        DisplayDescription = (TextView) findViewById(R.id.editDescription);

        BookCover = (ImageView) findViewById(R.id.BookCover);

        GoToForum = (Button) findViewById(R.id.GoToForum); //TODO: GO TO ACTIVITy
        Request_Cancel = (Button) findViewById(R.id.request_cancel);
        Edit = findViewById(R.id.Edit);

        viewRequests = (RecyclerView) findViewById(R.id.viewRequests);



        //Get what was passed in and display it
        Intent intent = getIntent();
        final Book_Instance book = (Book_Instance) intent.getSerializableExtra("book"); //Get the book

        DisplayTitle.setText(book.getTitle());
        DisplayAuthor.setText(book.getAuthor());
        DisplayISBN.setText(book.getISBN());
        DisplayOwner.setText(book.getOwner());
        DisplayStatus.setText(book.getStatus());
        //DisplayDescription.setText(book.get); TODO: Descriptions?

        //TODO: Make this the actual user
        final User globalUser = new User("Revan", "email", "location");

        //Set the visibility of Edit + cardView
        if (globalUser.getUserName().equals(book.getOwner()))
        {
            Edit.setVisibility(View.VISIBLE); //SHOW the button
            Request_Cancel.setVisibility(View.INVISIBLE);
            viewRequests.setHasFixedSize(true);
            viewRequests.setLayoutManager(new LinearLayoutManager(this));

            //for Testing
            BorrowRequest br = new BorrowRequest("revan", "revan", "123", 123);
            BorrowRequest br2 = new BorrowRequest("revan", "revan", "123", 123);
            BorrowRequest br3 = new BorrowRequest("revan", "revan", "123", 123);

            ArrayList<BorrowRequest> l = new ArrayList<>();
            l.add(br);
            l.add(br2);
            l.add(br3);

            requestAdapter = new BorrowRequestAdapter(this, l);
            viewRequests.setAdapter(requestAdapter);
            requestAdapter.notifyDataSetChanged();
        }else{
            viewRequests.setVisibility(viewRequests.INVISIBLE);
        }

        //Set appropriate text for the button at the bottom
        if ( isRequested){
            Request_Cancel.setText(R.string.cancel_request);
            isRequested = true;
        }else{
            Request_Cancel.setText(R.string.request_book);
            isRequested = false;
        }

        //Set On-Click listeners

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_Book_Instance_Activity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });

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

        //TODO: DB
        Request_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Case cancel book request
                if (isRequested){
                    Request_Cancel.setText(R.string.request_book);
                    isRequested = false;

                    //TODO: remove from database

                }else{
                    //Case Request book
                    Request_Cancel.setText(R.string.cancel_request);

                    Request request = new Request(globalUser.getUserName(), ,"borrow" );
                    isRequested = true;

                    //TODO: add request to database
                }
            }
        });
        */
    }
}
