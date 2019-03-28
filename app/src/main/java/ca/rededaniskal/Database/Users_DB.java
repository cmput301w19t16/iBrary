package ca.rededaniskal.Database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.BusinessLogic.myCallbackInt;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.User;

public class Users_DB {
    private DatabaseReference mDatabase;
    private User user;

    private String userName;
    private String email;
    private String phoneNumber;
    private String location;
    private int followCount;
    private String profilePic;
    private ArrayList<User> friends;
    private Book_List ownedBooks;
    private Book_List borrowedBooks; // Includes accepted
    private Book_List requestedBooks;

    public Users_DB() {
        getUserQuery();
    }

    private void getUserQuery(){
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void getUser(String uid, final myCallbackUser mcb){
        //Query query = mDatabase.orderByChild("uid").equalTo(uid);
        Query query =mDatabase.child(uid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
/*
                    userName = dataSnapshot.child("userName").getValue().toString();
                    String userid = dataSnapshot.child("uid").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    phoneNumber = dataSnapshot.child("phoneNumber").getValue().toString();
                    location = dataSnapshot.child("location").getValue().toString();
                    followCount = dataSnapshot.child("followerCount").getValue(int.class);
                    user = new User(userName, email, phoneNumber, location);
                    user.setFollowerCount(followCount);
                    user.setUID(userid);*/
                    user = dataSnapshot.getValue(User.class);
                    mcb.onCallback(user);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
