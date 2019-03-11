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

import android.content.Intent;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.BorrowRequestAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

//Author: Revan
public class Book_Details_Activity extends AppCompatActivity {

    TextView DisplayTitle;
    TextView DisplayAuthor;
    TextView DisplayISBN;
    TextView DisplayOwner;
    TextView DisplayStatus;
    TextView DisplayDescription;
    private getUserRequestsDB db;
    private User globalUser;

    ImageView BookCover;

    Button GoToForum;
    Button Request_Cancel;
    Button Edit;

    RecyclerView viewRequests;

    private Book_Instance book;
    private ArrayList<BorrowRequest> actual;

    BorrowRequestAdapter requestAdapter;

    boolean isRequested; //Auxillary variable for keeping track of where we need to go

    ArrayList<BorrowRequest> RL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__details_);
        db = new getUserRequestsDB();
        //Set the attributes to their corresponding views
        DisplayTitle = (TextView) findViewById(R.id.DisplayTitle);
        DisplayAuthor = (TextView) findViewById(R.id.DisplayAuthor);
        DisplayISBN = (TextView) findViewById(R.id.DisplayISBN);
        DisplayOwner = (TextView) findViewById(R.id.DisplayOwner);
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
        final User globalUser = new User("R", "email", "location");


        Edit.setVisibility(View.VISIBLE); //SHOW the button
        Request_Cancel.setVisibility(View.INVISIBLE);
//        viewRequests.setHasFixedSize(true);
        viewRequests.setLayoutManager(new LinearLayoutManager(this));

        //for Testing
        BorrowRequest br = new BorrowRequest("revan", "Revan", "123", 123);
        BorrowRequest br2 = new BorrowRequest("revan", "Revan", "123", 123);
        BorrowRequest br3 = new BorrowRequest("revan", "Revan", "123", 123);

        ArrayList<BorrowRequest> l = new ArrayList<>();
        l.add(br);
        l.add(br2);
        l.add(br3);
        RL = new ArrayList<>();
        requestAdapter = new BorrowRequestAdapter(this, RL);
        viewRequests.setAdapter(requestAdapter);
        requestAdapter.notifyDataSetChanged();

        //Set appropriate text for the button at the bottom
        if (isRequested) {
            Request_Cancel.setText(R.string.cancel_request);
            isRequested = true;
        } else {
            Request_Cancel.setText(R.string.request_book);
            isRequested = false;
        }


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

//        //TODO: DB
//        Request_Cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Case cancel book request
//                if (isRequested){
//                    Request_Cancel.setText(R.string.request_book);
//                    isRequested = false;
//
//                    //TODO: remove from database
//
//                }else{
//                    //Case Request book
//                    Request_Cancel.setText(R.string.cancel_request);
//
////                    Request request = new Request(globalUser.getUserName(), ,"borrow" );
//                    isRequested = true;
//
//                    //TODO: add request to database
//                }
//            }
//        });


//        db = new getUserRequestsDB();

    }
//    private void LoadRequests(){
//        Log.d(TAG, "*********----->LoadRequests");
//        globalUser = db.getUser();
//
//        //Set the visibility of Edit + cardView
//        if (db.getUsername().equals(book.getOwner()))
//        {
//            Log.d(TAG, "*********----->isOwner");
//            Edit.setVisibility(View.VISIBLE); //SHOW the button
//            Request_Cancel.setVisibility(View.INVISIBLE);
////            viewRequests.setHasFixedSize(true);
////            viewRequests.setLayoutManager(new LinearLayoutManager(this));
//
//            ArrayList<BorrowRequest> requests = db.getRequestList();
//            Log.d(TAG, "*********----->Length"+requests.size());
//            ArrayList<BorrowRequest> actual = new ArrayList<>();
//
//            for(int i = 0; i < requests.size(); i++){
//                Log.d(TAG, "*********----->"+requests.get(i).getIsbn());
//                if(requests.get(i).getIsbn().equals(book.getISBN())){
//                    Log.d(TAG, "*********----->At least 1");
//                    actual.add(requests.get(i));
//                }
//
//            }
//
////            requestAdapter = new BorrowRequestAdapter(this, actual);
////            viewRequests.setAdapter(requestAdapter);
////            requestAdapter.notifyDataSetChanged();
//        }else{
//            viewRequests.setVisibility(viewRequests.INVISIBLE);
//        }
//
//
//    }

    private void returnToLogin() {
        startActivity(new Intent(this, Login_Activity.class));
    }

    public class getUserRequestsDB{
        private FirebaseAuth mAuth;
        private String email;
        private String username;
        private FirebaseUser user;
        private DatabaseReference mDatabase;
        private ArrayList<BorrowRequest> requestList;
        private User User;

        public getUserRequestsDB() {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            requestList = new ArrayList<BorrowRequest>();
            if (user != null) {
                email = user.getEmail();
                getUserDetails();
//                getUserRequestSender();
//                getUserRequestRecipent();
//



            } else {
                returnToLogin();

            }
        }

        public String getUsername() {
            return username;
        }

        public ca.rededaniskal.EntityClasses.User getUser() {
            return User;
        }

        public ArrayList<BorrowRequest> getRequestList() {
            return requestList;
        }

        private void getUserDetails(){
            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("email")
                    .equalTo(email);

            Log.d(TAG, "*********----->"+email);
            query.addListenerForSingleValueEvent(valueEventListener);


        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.d(TAG, "*********----->"+snapshot.getValue());
                        User = snapshot.getValue(User.class);
                        username = User.getUserName();
                    }
                    getUserRequestRecipent();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


//        private void getUserRequestSender(){
//            Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
//                    .orderByChild("senderUserName")
//                    .equalTo(username);
//
//            Log.d(TAG, "*********----->"+username);
//
//            query.addListenerForSingleValueEvent(valueEventListener1);
//
//
//        }
//
////        ValueEventListener valueEventListener1 = new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////                Log.d(TAG, "*********----->onDataChange1");
////                if (dataSnapshot.exists()) {
////                    Log.d(TAG, "*********----->exists");
////                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
////                        BorrowRequest request = snapshot.getValue(BorrowRequest.class);
////                        requestList.add(request);
////                    }
////                }
////                getUserRequestRecipent();
////
////            }
////
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////
////            }
////        };

        private void getUserRequestRecipent(){
            Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                    .orderByChild("recipientUserName")
                    .equalTo(username);

            Log.d(TAG, "*********----->"+username);
            query.addListenerForSingleValueEvent(valueEventListener2);

        }

        ValueEventListener valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "*********----->onDataChange2");
                RL.clear();
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        BorrowRequest request = snapshot.getValue(BorrowRequest.class);
                        RL.add(request);
                        requestAdapter.notifyDataSetChanged();
//                        actual.add(request);
                    }
                    Log.d(TAG, "*********----->length"+RL.size());
//                    requestAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

}

