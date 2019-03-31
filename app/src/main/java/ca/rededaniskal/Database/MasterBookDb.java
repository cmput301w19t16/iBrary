package ca.rededaniskal.Database;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.Master_Book;


public class MasterBookDb extends Entity_Database {
    DatabaseReference mainRef = db.getReference(References.MASTERBOOK.reference());
    DatabaseReference authorindexRef = db.getReference(References.INDICESAUTHOR.reference());
    DatabaseReference titleindexRef = db.getReference(References.INDICESTITLE.reference());




    public MasterBookDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return mainRef;
    }

    public DatabaseReference getAuthorindexRef() {
        return authorindexRef;
    }

    public DatabaseReference getTitleindexRef() {
        return titleindexRef;
    }

    public void addMasterBook(Master_Book master_book){
        mainRef.child(master_book.getISBN()).setValue(master_book);
        ArrayList keys_a = ArrayUtils.toArrayList(master_book.getAuthor().split("\\s+") );
        ArrayList keys_t = ArrayUtils.toArrayList(master_book.getTitle().split("\\s+"));
       update_author_index(keys_a, master_book.getISBN());
       update_title_index(keys_t, master_book.getISBN());




    }

    public void update_author_index(ArrayList<String> keywords, String isbn){



        DatabaseReference index = db.getReference(References.INDICESAUTHOR.reference());
        for (String key: keywords){

                    index.child(key).setValue(isbn);
            }

    }

    public void update_title_index(ArrayList<String> keywords, String isbn){
        DatabaseReference index = db.getReference(References.INDICESTITLE.reference());
        for (String key: keywords){

            index.child(key).setValue(isbn);
        }

    }










}
