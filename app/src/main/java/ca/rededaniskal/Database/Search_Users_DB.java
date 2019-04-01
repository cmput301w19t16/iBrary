package ca.rededaniskal.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.EntityClasses.User;

public class Search_Users_DB {
    private Search_Fragment parent;
    private String search_string;

    public Search_Users_DB(Search_Fragment parent, String search_string){
        this.parent = parent;
        this.search_string = search_string;
    }

    // Returns all users that have a matching username in the database to that which was searched
    public void getUserMatches(){
        Log.d("getUserMatches", "**************In it");
        Query query = FirebaseDatabase.getInstance().getReference("Users")
                .orderByChild("userName")
                .equalTo(search_string);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.d("getUserMatches", "**************exists");
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        User user = d.getValue(User.class);
                        ArrayList<User> users = new ArrayList<>();
                        users.add(user);
                        Log.d("getUserMatches", "**************Usrename "+user.getUserName());
                        parent.update_users(users);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
