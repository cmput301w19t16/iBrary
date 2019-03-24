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

public class Search_Books_Db extends iBrary_Database {
    Search_Logic parent;
    String Order;
    String Equal;


    public Search_Books_Db(Search_Logic p, String filter, String is) {
        super();
        parent = p;
        Order = filter;
        Equal = is;

    }

    @Override
    public void update() {
        contruct_query();
    }





    public void contruct_query(){
        Query query;
        DatabaseReference ref = getReference(References.MASTERBOOK);
        if (Equal.equals("owner"))
            query= ref.child("instances").orderByKey().equalTo(Equal);
        else query = ref.child("book").orderByChild(Order).equalTo(Equal);

        query.addListenerForSingleValueEvent(queryListener);

    }

    ValueEventListener queryListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                ArrayList<Master_Book> m = new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Master_Book masterbook = d.child("book").getValue(Master_Book.class);
                    m.add(masterbook);

                }
                parent.addIntersection(m);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            databaseError.toException();

        }
    };


}
