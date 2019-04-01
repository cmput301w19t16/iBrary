/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View all your requested and borrowed books
 *
 * ISSUES:
 * Needs DB support
 *
 */
package ca.rededaniskal.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;

import ca.rededaniskal.BusinessLogic.brAdapter;
import ca.rededaniskal.BusinessLogic.myCallbackBIList;
import ca.rededaniskal.BusinessLogic.myCallbackBRList;
import ca.rededaniskal.BusinessLogic.myCallbackExchangeList;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.Database.BookExchangeDb;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Borrow_Req_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.R;

/**
 * This activity allows the user to view all the books that they had borrowed or requested.
 * The activity allows the user to toggle which activities they wish to see by using the switches
 * at the top of the activity which will filter the books displayed based on their status.
 *
 * @author Revan, Skye
 */

public class View_Borrowed_Requested_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Switch toggleRequested;
    private Switch toggleBorrowed;
    private String uid;
    private ArrayList<Book_Instance> borrowedBooks, requestedBooks;
    private ArrayList<BorrowRequest> bookRequests;
    private Boolean borrowedReady, requestedReady, borrowedWanted, requestedWanted;
    private brAdapter Adapter;
    private Activity act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__borrowed__requested_);
        act = this;

        recyclerView = findViewById(R.id.ViewBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //TODO: make this get a list of requested books, and borrowed books seperately
        borrowedReady = false;
        requestedReady = false;

        toggleRequested = findViewById(R.id.Requested);
        toggleBorrowed = findViewById(R.id.Borrowed);
        getSwitchValues();

        View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSwitchValues();
            }
        };
        toggleRequested.setOnClickListener(onclick);
        toggleBorrowed.setOnClickListener(onclick);

        getBookRequests();
        getBorrowedBookIDs();
    }

    private void getSwitchValues(){
        borrowedWanted = toggleBorrowed.isChecked();
        requestedWanted = toggleRequested.isChecked();
        tryFillData();
    }

    //For the requests
    private void getBookRequests(){
        //get the list of book ids you have borrowed
        //then get the list of bookinstances using those ids
        Borrow_Req_DB brdb = new Borrow_Req_DB();
        myCallbackBRList mcbr = new myCallbackBRList() {
            @Override
            public void onCallback(ArrayList<BorrowRequest> borrowRequests) {
                bookRequests = borrowRequests;
                getRequestedBooks();
            }
        };
        Log.d("getBookRequests:", " Reached!");
        brdb.getUsersSentRequests(uid, mcbr);
    }

    //Books that are requested
    private void getRequestedBooks(){
        BookInstanceDb bidb = new BookInstanceDb();
        myCallbackBIList mcbi = new myCallbackBIList() {
            @Override
            public void onCallback(ArrayList<Book_Instance> biList) {
                requestedBooks = biList;
                requestedReady = true;
                tryFillData();
            }
        };
        ArrayList<String> reqStrings = new ArrayList<>();
        for (BorrowRequest br : bookRequests){
            reqStrings.add(br.getBookId());
        }
        Log.d("getRequestedBooks:", " Reached!");
        bidb.getListOfBooks(reqStrings, mcbi);
    }

    private void getBorrowedBookIDs() {
        BookInstanceDb bidb = new BookInstanceDb();
        myCallbackBIList mcbi = new myCallbackBIList() {
            @Override
            public void onCallback(ArrayList<Book_Instance> biList) {
                borrowedBooks = biList;
                borrowedReady = true;
                tryFillData();
            }
        };
        bidb.getUsersBorrowedBooks(uid, mcbi);
    }

    private void tryFillData(){
        Log.d("tryFillData: ", "borrowedReady == " + Boolean.toString(borrowedReady) +
                " requestedReady == " + Boolean.toString(requestedReady));
        if (borrowedReady && requestedReady){
            Adapter = new brAdapter(act, borrowedBooks, requestedBooks, borrowedWanted, requestedWanted);
            recyclerView.setAdapter(Adapter);
            Adapter.notifyDataSetChanged();
        }
    }
}
