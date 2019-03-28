package ca.rededaniskal.Database;
    /*author Skye*/
//Interacts with the Firebase when a user adds a book to ther library

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.Random;

import ca.rededaniskal.BusinessLogic.Photo_GoogleBooksAPI;
import ca.rededaniskal.BusinessLogic.Title_Author_GoogleBooksAPI;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;

import static java.security.AccessController.getContext;

public class AddBookDb implements AsyncResponse {
        public final String TAG = "AddBookDb";
        MasterBookDb masterdb;
        BookInstanceDb instancedb;
        boolean bookAdded;
        private Bitmap googleCover;
        private Title_Author_GoogleBooksAPI asyncTask;
        private Context context;
        private URL googleCoverURL;
        private URL myCoverURL;



        String success;
        Book_Instance book_instance;

        public AddBookDb(Book_Instance book_instance, Context context) {

            //Creates a new reference to the correct path in the Firebase
            //Book instances are stored under there unique id, under my-books,
            //under unique user Uid, under book-instances.


            this.book_instance = book_instance;
            this.masterdb = new MasterBookDb();
            this.instancedb = new BookInstanceDb();
            this.context = context;

            update();

        }


        public void update() {
            addBookToDatabase();
            if (!masterdb.checkExists(masterdb.getReference().child(book_instance.getISBN()))) {
                book_instance.setAuthor("Williams");



            }
            asyncTask.delegate = this;
            String isbn = book_instance.getISBN();
            asyncTask = new Title_Author_GoogleBooksAPI(context,null, null, null, 1);
            asyncTask.execute(isbn);

            Master_Book mb = new Master_Book(book_instance.getTitle(), book_instance.getAuthor(), book_instance.getISBN(), googleCoverURL);
            masterdb.addMasterBook(mb);

        }

        @Override
        public void processFinish(Bitmap output){
            googleCover = output;
            googleCoverURL = new Photos(context).getURLFromBitmap(googleCover);
        }

        public void addBookToDatabase() throws NullPointerException {
            success = instancedb.getStorageId();
            book_instance.setBookID(success);
            bookAdded = instancedb.addBookInstance(book_instance);
        }
    }