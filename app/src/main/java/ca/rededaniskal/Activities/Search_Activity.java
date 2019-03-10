package ca.rededaniskal.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.R;

public class Search_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



/*

private class SearchBookDB{


    DatabaseReference db;
    Context context;

    public SearchBookDB() {
    }

    public void getMasterBooks(){
        db =FirebaseDatabase.getInstance().getReference("master-books");
        db.addValueEventListener(masterListener);


    }
    ValueEventListener masterListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            for (DataSnapshot d: dataSnapshot.getChildren()){

                d.getValue(Master_Book.class);


            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
    }

    */







}

