package ca.rededaniskal.BusinessLogic;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.rededaniskal.Database.Add_Remove_Request_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;

public class Book_Details_Logic {
    private Book_Instance book;
    private boolean is_requested;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Add_Remove_Request_DB db;

    public Book_Details_Logic(Book_Instance book, boolean is_requested){
        this.book = book;
        this.is_requested = is_requested;

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user != null){
            if(is_requested){
                createRequest();
            }else{
                deleteRequest();
            }
        }

    }

    private void createRequest(){
        String uid = user.getUid();
        String owner = book.getOwner();
        String isbn = book.getISBN();
        String book_id = book.getId();
        BorrowRequest request = new BorrowRequest(uid, owner, isbn, book_id);

        db = new Add_Remove_Request_DB(request, book);
    }

    private void deleteRequest(){
        String uid = user.getUid();
        db = new Add_Remove_Request_DB(book, uid);
    }

}
