package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.BusinessLogic.BorrowRequestAdapter;
import ca.rededaniskal.BusinessLogic.Write_Notification_Logic;
import ca.rededaniskal.EntityClasses.Book;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;

import static android.support.constraint.Constraints.TAG;

public class Write_Request_DB {


    private BorrowRequest request;
    private DatabaseReference mDatabase;
    private String key;
    private Book_Instance book;
    private String sender_UID;
    private boolean delete;


    public Write_Request_DB(BorrowRequest request, Book_Instance book) {
        this.book = book;
        Log.d(TAG, "*********------> Write_Request_DB");
        this.request = request;
        Log.d(TAG, "*********------> Request sender UID: " + request.getsenderUID());
        createRequest();

    }

    public Write_Request_DB(Book_Instance book, String sender_UID){
        this.delete = true;
        this.sender_UID = sender_UID;
        this.book = book;
        Log.d(TAG, "*********------> Write_Request_DB");
        Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("isbn")
                .equalTo(book.getISBN());
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    public Write_Request_DB(BorrowRequest request, boolean delete){
        this.request = request;
        this.delete = delete;
        Log.d(TAG, "*********------> Write_Request_DB");
        Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                .orderByChild("isbn")
                .equalTo(request.getIsbn());
        query.addListenerForSingleValueEvent(valueEventListener);
    }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d(ContentValues.TAG, "*********----->onDataChange: Write_Request_DB");
            if (dataSnapshot.exists()) {
                Log.d(ContentValues.TAG, "*********----->exists");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    BorrowRequest req = snapshot.getValue(BorrowRequest.class);
                    if(req.getsenderUID().equals(request.getsenderUID())) {
                        key = snapshot.getKey();
                    }
                }
                if(delete) {
                    deleteRequest();
                }else{
                    updateRequest();
                }
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {}
    };


    private void createRequest(){
        Log.d(TAG, "*********------> createRequest");
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        String k = mDatabase.push().getKey();
        mDatabase.child(k).setValue(request);
        String owner = book.getOwner();
        String requestType = "Book Requested";

        Write_Notification_Logic new_notif = new Write_Notification_Logic(owner, request.getsenderUID(), k, requestType);
        Update_Book_DB book_status = new Update_Book_DB(book);
    }

    private void deleteRequest(){
        Log.d(TAG, "*********------> deleteRequest");
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        mDatabase.child(key).removeValue();

        Write_Notification_Logic delete_notif = new Write_Notification_Logic(key);
        Update_Book_DB book_status = new Update_Book_DB(request.getrecipientUID(), request.getIsbn());
    }

    private void updateRequest(){
        Log.d(TAG, "*********------> updateRequest");
        mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
        mDatabase.child(key).setValue(request);

        if(request.getStatus().equals("Accepted")){
            String requestType = "Book Request Accepted";
            Write_Notification_Logic delete_notif = new Write_Notification_Logic(request.getsenderUID(), request.getrecipientUID(), key, requestType);
        }
        Update_Book_DB book_status = new Update_Book_DB(request.getrecipientUID(), request.getIsbn());
    }


}
