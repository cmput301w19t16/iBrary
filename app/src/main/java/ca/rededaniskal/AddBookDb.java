package ca.rededaniskal;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

final class AddBookDb {
    FirebaseDatabase db;
    DatabaseReference bookRef;
    String success;

    public AddBookDb() {
        this.db = FirebaseDatabase.getInstance();
        this.bookRef = db.getReference().child("book-instances");

    }

    public String addBookToDatabase(BookInstance bookInstance){
        String slash = "/";

        success =bookRef.push().getKey();

        HashMap<String, Object> bookChildUpdates= new HashMap<>();
        bookChildUpdates
                .put("user-books/"+bookInstance.getOwner()+slash+success, bookInstance);
        if (bookInstance.getStatus()=="b") {
            bookChildUpdates
                    .put("borrowed-books/" + bookInstance.getPossessor()+slash + success, bookInstance);
        }
        bookChildUpdates
                .put("ISBN/"+bookInstance.getISBN()+slash +success, bookInstance);

        if (bookRef.updateChildren(bookChildUpdates).isSuccessful()){
            bookInstance.setBookID(success);
        }
        else success = "";



        return success;
    }

}
