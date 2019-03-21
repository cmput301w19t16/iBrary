package ca.rededaniskal.Database;
/*author Skye*/
//Interacts with the Firebase when a user adds a book to ther library
import android.support.annotation.NonNull;
import android.util.Log;

        import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.ValueEventListener;

        import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;

public class AddBookDb extends iBrary_Database {
    public final String TAG = "AddBookDb";
    DatabaseReference bookRef;
    DatabaseReference masterRef;

    String success;
    Book_Instance book_instance;

    public AddBookDb(Book_Instance book_instance) {
        super();
        //Creates a new reference to the correct path in the Firebase
        //Book instances are stored under there unique id, under my-books,
        //under unique user Uid, under book-instances.

        String user = getUID();
        this.bookRef = getReference(References.BOOKINSTANCE)
                .child(user);
        this.book_instance = book_instance;
        this.masterRef = getReference(References.MASTERBOOK).child(book_instance.getISBN());
        update();

    }




    @Override
    public void update(){
        boolean YES = addBookToDatabase();
        if (YES&&checkExists(masterRef)){
            updateMaster();
        }
        else {
            addMaster();
        }

        updateFeed();


    }


    public boolean addBookToDatabase() throws NullPointerException{

        success =bookRef.push().getKey();
        book_instance.setBookID(success);
        Log.d(TAG, "***********---->" +book_instance.getBookID());

        if (bookRef.child(success).setValue(book_instance).isSuccessful()){
            return true;

        }
        else return false;

    }



    public void updateMaster(){
        masterRef.child("instances").child(book_instance.getOwner()).child(book_instance.getBookID()).setValue(true);

    }

    public void addMaster(){
        Master_Book mb = new Master_Book(book_instance.getTitle(), book_instance.getAuthor(), book_instance.getISBN());
        masterRef.child("book").setValue(mb);
        masterRef.child("instances").child(book_instance.getOwner()).child(book_instance.getBookID()).setValue(true);


    }

    public void updateFeed(){}

}
