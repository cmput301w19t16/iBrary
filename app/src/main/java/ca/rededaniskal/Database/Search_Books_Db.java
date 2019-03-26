package ca.rededaniskal.Database;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.Search_Logic;
import ca.rededaniskal.EntityClasses.Master_Book;

public class Search_Books_Db {
    Search_Logic parent;
    String Order;
    String Equal;
    MasterBookDb masterBookDb;
    BookInstanceDb bookInstanceDb;




    public Search_Books_Db(Search_Logic p, String filter, String is) {
        parent = p;
        Order = filter;
        Equal = is;

        queryData();

    }

    public void queryData(){
        final ArrayList<Master_Book> searchlist = new ArrayList<>();
        Query q = masterBookDb.getReference().orderByChild(Order).equalTo(Equal);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    for (DataSnapshot d: dataSnapshot.getChildren()){
                        searchlist.add(d.getValue(Master_Book.class));

                    }
                    parent.addIntersection(searchlist);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }









}

