package ca.rededaniskal.Database;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import ca.rededaniskal.Activities.Book_Details_Activity;

public class BookDetailsdb extends iBrary_Database{
    Book_Details_Activity parent;
    DatabaseReference requestBookRef;
    private boolean failed;
    String bookId;

    public void BookDetailsdb(Book_Details_Activity bda, String bookid){
        parent = bda;
        this.bookId = bookid;
    }

    @Override
    public void update() {

    }

    public boolean bookInUserRequests(){
        exists = false;

        String user = getUID();
       requestBookRef= getReference(References.BOOKREQUEST);
       Query requested = requestBookRef.orderByChild("bookId").equalTo(this.bookId);
       requested.addListenerForSingleValueEvent(queryRequestListener);

       return exists;




    }

    ValueEventListener queryRequestListener =new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot d:dataSnapshot.getChildren()){
                exists =true;
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public boolean getFailed(){return failed;}

    ValueEventListener requestedListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                parent.setTrue();

            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            databaseError.toException();

        }
    };



}
