package ca.rededaniskal.Database;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.BusinessLogic.Search_Logic;
import ca.rededaniskal.EntityClasses.Master_Book;

public class Search_Books_Db {
    Search_Fragment parent;
    String Order;
    String Equal;
    Query query;
    Master_Book mb;
    MasterBookDb masterBookDb;
    ArrayList<Master_Book> searchlist;
    ArrayList<String> isbns;
    String isbn;

    public Search_Books_Db(Search_Fragment p, ArrayList<String>Isbn) {
        parent = p;

        masterBookDb = new MasterBookDb();
       isbns = Isbn;
       if (isbns==null) {isbns = new ArrayList<>();}

    }


    public Search_Books_Db(Search_Fragment p, String filter, String e) {
        parent = p;
        Order = filter;
        Equal = e;

        masterBookDb = new MasterBookDb();
        searchlist = new ArrayList<>();

    }

    public Search_Books_Db(Search_Fragment p, String e) {
        parent = p;
        Equal = e;
        masterBookDb = new MasterBookDb();
        searchlist = new ArrayList<>();

    }

    public void queryData() {
        query = masterBookDb.getReference().orderByChild(Order).equalTo(Equal);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        searchlist.add(d.getValue(Master_Book.class));
                    }
                    if (!searchlist.isEmpty()){
                    parent.update_books(searchlist);}

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void queryTitleData() {
        query = masterBookDb.getTitleindexRef().orderByKey().equalTo(Equal);

        //query = masterBookDb.getReference().orderByChild(Order).equalTo(Equal);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        isbn = d.getValue(String.class);

                    }
                    Log.d("isbn", "*********----->Got this Titl book: "+ isbn);
                    queryISBNData(isbn);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void queryAuthorData() {
        query = masterBookDb.getAuthorindexRef().orderByKey().equalTo(Equal);

        //query = masterBookDb.getReference().orderByChild(Order).equalTo(Equal);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                       isbn = d.getValue(String.class);
                       break;
                    }
                    Log.d("isbn", "*********----->Got this Author book: "+isbn);
                   queryISBNData(isbn);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void queryISBNData(String isbn) {
        Log.d("ISBN", "***********------> "+isbn);
        query = masterBookDb.getReference().orderByKey().equalTo(isbn);

        //query = masterBookDb.getReference().orderByChild(Order).equalTo(Equal);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        mb = d.getValue(Master_Book.class);
                    }
                    parent.addBookToAdapter(mb);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void getSingleMasterBook(){
        masterBookDb.getReference().child(Equal).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                parent.addBookToAdapter(dataSnapshot.getValue(Master_Book.class));}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
