package ca.rededaniskal.Database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.BusinessLogic.Search_Logic;
import ca.rededaniskal.EntityClasses.Master_Book;

public class Search_Books_Db {
    Search_Fragment parent;
    String Order;
    String Equal;
    MasterBookDb masterBookDb;





    public Search_Books_Db(Search_Fragment p, String filter, String is) {
        parent = p;
        Order = filter;
        Equal = is;
        masterBookDb = new MasterBookDb();

        queryData();

    }

    public void queryData(){
        final ArrayList<Master_Book> searchlist = new ArrayList<>();
       masterBookDb.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        searchlist.add(d.getValue(Master_Book.class));


                    }
                   parent.update_books(searchlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }









}

