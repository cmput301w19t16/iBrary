/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View the details of a book if it is not your book
 * Send a request for a book if it is not your book
 * View the requests for this book if it is your book
 * Navigate to editing the book if it is your book
 *
 * ISSUES:
 * Needs to DB support
 *
 */
package ca.rededaniskal.Activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.Book_Details_Logic;
import ca.rededaniskal.BusinessLogic.BorrowRequestAdapter;

import ca.rededaniskal.BusinessLogic.LoadImage;

import ca.rededaniskal.BusinessLogic.myCallbackBRList;
import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Borrow_Req_DB;
import ca.rededaniskal.Database.Username_For_Book_Details_DB;

import ca.rededaniskal.Database.requestsOnBookDB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;
import ca.rededaniskal.Database.BookDetailsdb;

/**
 * This activity lets the user view a book's details.
 * If they own the book, it also lets them go to the edit book activity.
 */

//Author: Revan
public class Book_Details_Activity extends AppCompatActivity {

    TextView DisplayTitle;
    TextView DisplayAuthor;
    TextView DisplayISBN;
    TextView DisplayOwner;
    TextView DisplayStatus;
    TextView DisplayPosessor;
    private Book_Details_Logic logic;

    ImageView DisplayBookCover;

    Button GoToForum;
    Button Request_Cancel;
    Button Edit;

    RecyclerView viewRequests;
    private Book_Instance book;

    BorrowRequestAdapter requestAdapter;
    ArrayList<BorrowRequest> l;
    Book_Details_Activity thisone;

    private FirebaseAuth mAuth;
    private String uid;

    boolean canReturn = false;
    boolean isRequested; //Auxillary variable for keeping track of where we need to go

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__details_);

        thisone = this;

        //Set the attributes to their corresponding views
        DisplayTitle = (TextView) findViewById(R.id.DisplayTitle);
        DisplayAuthor = (TextView) findViewById(R.id.DisplayAuthor);
        DisplayISBN = (TextView) findViewById(R.id.DisplayISBN);
        DisplayOwner = (TextView) findViewById(R.id.DisplayOwner);
        DisplayStatus = (TextView) findViewById(R.id.DisplayStatus);
        DisplayPosessor = findViewById(R.id.viewPosessor);

        DisplayBookCover = (ImageView) findViewById(R.id.BookCover);

        GoToForum = (Button) findViewById(R.id.GoToForum); //TODO: GO TO ACTIVITy
        Request_Cancel = (Button) findViewById(R.id.request_cancel);
        Edit = findViewById(R.id.Edit);

        viewRequests = (RecyclerView) findViewById(R.id.viewRequests);


        mAuth = FirebaseAuth.getInstance();

        //Get what was passed in and display it
        Intent intent = getIntent();
        book = (Book_Instance) intent.getSerializableExtra("book"); //Get the book
        Username_For_Book_Details_DB dbu = new Username_For_Book_Details_DB(this, book);

        DisplayTitle.setText(book.getTitle());
        DisplayAuthor.setText(book.getAuthor());
        DisplayISBN.setText(book.getISBN());
        DisplayOwner.setText(book.getOwner());
        DisplayStatus.setText(book.getStatus());
        DisplayPosessor.setText(book.getPossessor());

        if(book.getCover() != null || book.getCover() != ""){
            LoadImage loader = new LoadImage(DisplayBookCover);
            loader.execute(book.getCover());
        }

        String globalUser = FirebaseAuth.getInstance().getUid();

        //Set the visibility of Edit + cardView
        if (globalUser.equals(book.getOwner())) {
            Edit.setVisibility(View.VISIBLE); //SHOW the button
            Request_Cancel.setVisibility(View.INVISIBLE);
            viewRequests.setHasFixedSize(true);
            viewRequests.setLayoutManager(new LinearLayoutManager(this));

            Borrow_Req_DB brdb = new Borrow_Req_DB();
            myCallbackBRList mcbrl = new myCallbackBRList() {
                @Override
                public void onCallback(ArrayList<BorrowRequest> borrowRequests) {
                    requestAdapter = new BorrowRequestAdapter(thisone, borrowRequests);
                    viewRequests.setAdapter(requestAdapter);
                    requestAdapter.notifyDataSetChanged();
                }
            };

            brdb.getBooksBorrowRequests(book.getBookID(), mcbrl);

        } else {
            viewRequests.setVisibility(viewRequests.INVISIBLE);
        }

        BookDetailsdb db = new BookDetailsdb(this, book.getBookID());
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        Borrow_Req_DB brdb = new Borrow_Req_DB();

        //isRequested = db.bookInUserRequests();
        myCallbackBool mcbb = new myCallbackBool() {
            @Override
            public void onCallback(Boolean value) {
                isRequested = value;
                continueCreating();
            }
        };

       db.bookInUserRequests(mcbb);


        //brdb.requestExists(book.getBookID(), uid, mcbb);
        //continueCreating();
    }

    private void continueCreating(){


        //Set appropriate text for the button at the bottom
        if (book.getStatus().equals("Requested") && isRequested) {
            //If I Requested the book
            Request_Cancel.setText(R.string.cancel_request);

        }else if (book.getStatus().equals("Borrowed")){
            //If book is borroed by someone other than myself
            Request_Cancel.setVisibility(View.INVISIBLE);

        } else if (book.getPossessor().equals(uid)){
            //If i am the one in possession of book but not the owner

            Request_Cancel.setText("Return This Book");
            canReturn = true;

        } else {
            Request_Cancel.setText(R.string.request_book);

        }

        //Set On-Click listeners

        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Edit_Book_Instance_Activity.class);
                intent.putExtra("book", book);
                startActivity(intent);
                finish();
            }
        });

        //TODO: Make this go to forum

        GoToForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Forum_Activity.class);
                startActivity(intent);
            }
        });


        final Book_Details_Activity thisone = this;

        Request_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Case cancel book request
                if (isRequested){
                    Request_Cancel.setText(R.string.request_book);
                    isRequested = false;
                    /*Borrow_Req_DB brdb = new Borrow_Req_DB();
                    brdb.removeBorrowRequest(book.getBookID(), uid);*/
                    //BorrowRequest request = new BorrowRequest(uid, book.getOwner(), book.getISBN(),book.getBookID());
                    //Notification notification = new Notification(book.)
                    //brdb.createBorrowRequest(request, notification);


                }else if(canReturn){
                    //TODO: DB

                    BorrowRequest request = new BorrowRequest( book.getOwner() , uid, book.getISBN(), book.getBookID() );
                    Intent intent = new Intent(v.getContext(), Establish_Exchange_Details_Activity.class);
                    intent.putExtra("BorrowRequestObject", request);
                    intent.putExtra("Returning", true);
                    v.getContext().startActivity(intent);
                    //logic = new Book_Details_Logic(book, isRequested);
                }else{
                    //Case Request book
                    Request_Cancel.setText(R.string.cancel_request);
                    isRequested = true;
                    //logic = new Book_Details_Logic(book, isRequested);
                }
                logic = new Book_Details_Logic(book, isRequested);
            }
        });

    }

    ValueEventListener valueEventListener2 = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(TAG, "*********----->onDataChange2");
//            l.clear();
            if (dataSnapshot.exists()) {
                Log.d(TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                    l.add(request);

                }

                Log.d(TAG, "*********----->" + l);
                //l.add(new BorrowRequest());
                requestAdapter.notifyDataSetChanged();

                Log.d(TAG, "*********----->length" + l.size());
//                    requestAdapter.notifyDataSetChanged();
            }

        }


        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public void setTrue() {
        this.isRequested = true;

    }

    public void setUsername(String username){
        DisplayOwner.setText(username);
    }

    public String getBookISBN(){return book.getISBN();}

    public void listClear(){l.clear(); return;}

    public void append(BorrowRequest r){l.add(r);return;}

    public int getLSize(){return l.size();}

    public void notifyRequest(){requestAdapter.notifyDataSetChanged();}


    private void returnToLogin() {
        startActivity(new Intent(this, Login_Activity.class));
    }

}



