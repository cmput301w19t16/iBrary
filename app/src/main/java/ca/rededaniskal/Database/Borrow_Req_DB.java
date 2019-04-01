package ca.rededaniskal.Database;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.myCallbackBRList;
import ca.rededaniskal.BusinessLogic.myCallbackBookRequest;
import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.EntityClasses.BorrowRequest;

import ca.rededaniskal.EntityClasses.Display_BorrowRequest;
import ca.rededaniskal.EntityClasses.User;
import static android.content.ContentValues.TAG;

import ca.rededaniskal.EntityClasses.Notification;


public class Borrow_Req_DB {
    private DatabaseReference mDatabase;
    private ArrayList<Display_BorrowRequest> brl;

    public Borrow_Req_DB() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public void getBorrowReq(String reqID, final myCallbackBookRequest mcbr) {

        Query query = mDatabase.child("BorrowRequests/" + reqID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    mcbr.onCallback(dataSnapshot.getValue(BorrowRequest.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });

    }

    public void getBooksBorrowRequests(final String bookID, final myCallbackBRList mcbrl) {
        final myCallbackStringList mcbsl = new myCallbackStringList() {
            @Override
            public void onCallback(final ArrayList<String> strList) {
                Query query = mDatabase.child("BorrowRequests");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            brl = new ArrayList<>();

                            for (String reqID : strList) {
                                BorrowRequest request = dataSnapshot.child(reqID).getValue(BorrowRequest.class);
                                Display_BorrowRequest display = new Display_BorrowRequest(request);
                                brl.add(display);
                            }
                            getUserInfo(mcbrl);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
                    }
                });
            }
        };
        getBooksBorrowRequestIDS(bookID, mcbsl);
    }



    public void getUserInfo(final myCallbackBRList mcbrl){
        for(int i =0; i < brl.size();i++){
            final int j = i;
            BorrowRequest request = brl.get(i).getRequest();
            String key = request.getsenderUID();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users").child(key);
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.d(TAG, "*********----->exists");


                        User user = dataSnapshot.getValue(User.class);

                        Display_BorrowRequest display = brl.get(j);
                        display.setUser(user);
                        brl.set(j, display);

                        mcbrl.onCallback(brl);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }



    public void getBooksBorrowRequestIDS(String bookID, final myCallbackStringList mcbsl) {
        Query query = mDatabase.child("BorrowRequests").orderByChild("bookId").equalTo(bookID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> al = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        al.add(snapshot.getKey());
                    }
                    mcbsl.onCallback(al);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });
    }

    //TODO: not fully implemented here.
    public void createBorrowRequest(BorrowRequest br){
        DatabaseReference key = mDatabase.child("BorrowRequests").push();
        key.setValue(br);
        String keyval = key.getKey();
        Notifications_DB ndb = new Notifications_DB();
        //Notification n = new Notification()
        //ndb.storeNotification();
    }

    public void getBRKeys(String bookId, final String senderId, final myCallbackStringList mcbsl){
        Query query = mDatabase.child("BorrowRequests").orderByChild("bookId").equalTo(bookId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    ArrayList<String> als = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if (snapshot.child("senderUID").getValue(String.class).equals(senderId)){
                            als.add(snapshot.getKey());
                        }
                    }
                    mcbsl.onCallback(als);
                }
                else{
                    mcbsl.onCallback(new ArrayList<String>());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(ContentValues.TAG, "WE GOOFED UP BUDDY");
            }
        });

    }

    public void removeBorrowRequest(String bookId, final String senderId){
        myCallbackStringList mcbsl = new myCallbackStringList() {
            @Override
            public void onCallback(ArrayList<String> strList) {
                Notifications_DB ndb = new Notifications_DB();
                for (String str : strList){
                    mDatabase.child("BorrowRequests/" + str).removeValue();
                    ndb.deleteNotification(str);
                }
            }
        };
        getBRKeys(bookId, senderId, mcbsl);
    }

    public void requestExists(String bookId, final String senderId, final myCallbackBool mcbb){
        myCallbackStringList mcbsl = new myCallbackStringList() {
            @Override
            public void onCallback(ArrayList<String> strList) {
                Boolean exists = false;
                for (String str : strList){
                    exists = true;
                }
                mcbb.onCallback(exists);
            }
        };
        getBRKeys(bookId, senderId, mcbsl);
    }
}