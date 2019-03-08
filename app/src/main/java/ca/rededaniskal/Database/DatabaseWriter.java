package ca.rededaniskal.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Forum;
import ca.rededaniskal.EntityClasses.Friend_Request;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.User;

public class DatabaseWriter {
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;


    public DatabaseWriter() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseRef = this.database.getReference();


    }



    public boolean updateBookInstance(Book_Instance bookInstance){
        DatabaseReference bookInstanceRef = this.databaseRef.child("book-instances");
        DatabaseReference masterBookRef = this.databaseRef.child("master-books");
        HashMap<String, Object> bookChildUpdates = new HashMap<String, Object>();
        HashMap<String, Object> masterChildUpdates = new HashMap<String, Object>();
        bookChildUpdates
                .put("user-books/"+bookInstance.getOwner()+bookInstance.getBookID(), bookInstance);
        if (bookInstance.getStatus()=="Borrowed") {
            bookChildUpdates
                    .put("borrowed-books/" + bookInstance.getPossessor() + bookInstance.getBookID(), bookInstance);
        }
        bookChildUpdates
                .put("ISBN/"+bookInstance.getISBN()+bookInstance.getBookID(), bookInstance);
        return bookInstanceRef.updateChildren(bookChildUpdates).isSuccessful();








    }
    public void writeNewMasterBook(Master_Book masterBook){

    }
    public void writeNewUser(User user){

    }
    public void writeNewRequest(BorrowRequest borrowRequest){

    }
    public void writeNewRequest(Friend_Request friendRequest){

    }

    public void writeNewPost(Post post){

    }
    public void writeNewForum(Forum forum){

    }





}
