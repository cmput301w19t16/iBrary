package ca.rededaniskal.Database;
    /*author Skye*/
//Interacts with the Firebase when a user adds a book to ther library

import com.google.firebase.provider.FirebaseInitProvider;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;

    public class AddBookDb {
        public final String TAG = "AddBookDb";
        MasterBookDb masterdb;
        BookInstanceDb instancedb;
        boolean bookAdded;


        String success;
        Book_Instance book_instance;

        public AddBookDb(Book_Instance book_instance) {

            //Creates a new reference to the correct path in the Firebase
            //Book instances are stored under there unique id, under my-books,
            //under unique user Uid, under book-instances.


            this.book_instance = book_instance;
            this.masterdb = new MasterBookDb();
            this.instancedb = new BookInstanceDb();

            update();

        }


        public void update() {
            addBookToDatabase();


                Master_Book mb = new Master_Book(book_instance.getTitle(), book_instance.getAuthor(), book_instance.getISBN());
                masterdb.addMasterBook(mb);






        }


        public void addBookToDatabase() throws NullPointerException {
            success = instancedb.getStorageId();
            book_instance.setBookID(success);
            bookAdded = instancedb.addBookInstance(book_instance);


        }


    }