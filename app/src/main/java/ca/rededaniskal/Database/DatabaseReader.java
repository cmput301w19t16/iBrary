package ca.rededaniskal.Database;
/*author Skye*/
/*import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.Post;

public class DatabaseReader {
    private FirebaseDatabase database;
    private DatabaseReference listRef;
    private Book_List bookList;
    private ArrayList<String> userList;
    private ArrayList<Master_Book> masterBookList;
    private ArrayList<Post> postList;

    private Book_Instance bookInstance;
    private String user;
    private Master_Book masterBook;



    public DatabaseReader() {
        database = FirebaseDatabase.getInstance();
        listRef = database.getReference();

    }





    public Book_List getFilteredBookList(String filterby, String filterVal){

        DatabaseReference userBooksRef = this.listRef.child("bookInstances");
        Query query = userBooksRef.orderByChild(filterby).equalTo(filterVal);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateBookList((Map<String,Object>) dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return this.bookList;
    }

    public ArrayList<String> getFilteredUserList(String filterby, String filterVal){

        Query userFriendsQuery = this.listRef.child("users").orderByChild(filterby).equalTo(filterVal);
        userFriendsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateStringList((Map<String,Object>) dataSnapshot);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return this.userList;
    }



    public ArrayList<Master_Book> getFilteredMasterBookList(String filterby, String filterVal) {



        Query listQuery = this.listRef.child("masterBooks").orderByChild(filterby).equalTo(filterVal);
        listQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                populateMasterBookList((Map<String, Object>) dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return this.masterBookList;
    }



    public Book_Instance getBookInstanceByID(String bookID) {
        DatabaseReference bookRef = listRef.child("bookInstances").child(bookID);

        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setBookInstance((Book_Instance) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return this.bookInstance;

    }

    public Master_Book getMasterBookInstanceByISBN(String masterBookIsbn) {
        DatabaseReference bookRef = listRef.child("masterBooks").child(masterBookIsbn);

        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setMasterBook((Master_Book) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return this.masterBook;

    }
    public String getUserByID(String userID) {
        DatabaseReference bookRef = listRef.child("users").child(userID);

        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setUser((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return this.user;

    }





    private void populateBookList(Map<String, Object> stringObjectMap) {
        this.bookList = new Book_List();
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            this.bookList.addBook((Book_Instance)entry.getValue());
        }

    }
    private void populateStringList(Map<String, Object> stringObjectMap) {
        this.userList = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            this.userList.add((String)entry.getValue());
        }

    }

    private void populateMasterBookList(Map<String, Object> stringObjectMap) {
        this.masterBookList = new ArrayList<Master_Book>();
        for (Map.Entry<String, Object> entry : stringObjectMap.entrySet()) {
            this.masterBookList.add((Master_Book)entry.getValue());
        }

    }

    public void setBookInstance(Book_Instance bookInstance) {
        this.bookInstance = bookInstance;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMasterBook(Master_Book masterBook) {
        this.masterBook = masterBook;
    }
}
*/


