package ca.rededaniskal.Database;

import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import ca.rededaniskal.Activities.Fragments.Search_Fragment;

import ca.rededaniskal.EntityClasses.Master_Book;

public class Search_Books_Db {

    private Search_Fragment parent;


    private String Equal;
    private Query query;
    private Master_Book mb;
    private MasterBookDb masterBookDb;

    private String isbn;


    public Search_Books_Db(Search_Fragment p, String e) {
        parent = p;
        Equal = e;
        masterBookDb = new MasterBookDb();
    }


    

    public void queryTitleData() {
        query = masterBookDb.getTitleindexRef().orderByKey().equalTo(Equal);



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

    public void queryISBNData(String ISBN) {
        Log.d("EqualVal", "***********------> " + ISBN);
        query = masterBookDb.getReference().orderByKey().equalTo(ISBN);

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

