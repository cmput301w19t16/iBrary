package ca.rededaniskal.Database;
    /*author Skye*/
//Interacts with the Firebase when a user adds a book to ther library

import ca.rededaniskal.BusinessLogic.myCallBackMasterBook;
import ca.rededaniskal.BusinessLogic.myCallBackString;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;

public class AddBookDb {
        public final String TAG = "AddBookDb";
        private MasterBookDb masterdb;
        private BookInstanceDb instancedb;
        boolean bookAdded;
        private static final int UPLOAD_REQUEST = 200;

        private myCallBackMasterBook mcmb;
        private Master_Book mb;
        private String success;
        private Book_Instance book_instance;
        private myCallBackString mcbstr;
       private String mbCoverURL;

        public AddBookDb(Book_Instance book_instance,String url) {

            //Creates a new reference to the correct path in the Firebase
            //Book instances are stored under there unique id, under my-books,
            //under unique user Uid, under book-instances.


            this.book_instance = book_instance;
            this.masterdb = new MasterBookDb();
            this.instancedb = new BookInstanceDb();
            this.mbCoverURL = url;

            update();

        }


        public void update() {
            addBookToDatabase();
            if (!masterdb.checkExists(masterdb.getReference().child(book_instance.getISBN()))) {
                book_instance.setAuthor("Williams");

            }

            mb = new Master_Book(book_instance.getTitle(), book_instance.getAuthor(), book_instance.getISBN());
            mb.setGoogleCover(mbCoverURL);


            /*mcbstr = new myCallBackString() {
                @Override
                public void onCallback(String url) {
                    mb.setGoogleCover(url);
                }
            };
            new Photos().BitmapToURLMB(bitmap, book_instance.getTitle(), book_instance.getISBN(), mcbstr);
            //mb.setGoogleCover(url);*/
            masterdb.addMasterBook(mb);
        }


        public void addBookToDatabase() throws NullPointerException {
            success = instancedb.getStorageId();
            book_instance.setBookID(success);
            bookAdded = instancedb.addBookInstance(book_instance);
        }
    }