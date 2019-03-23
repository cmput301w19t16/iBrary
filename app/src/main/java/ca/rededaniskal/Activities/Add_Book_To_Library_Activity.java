/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Add a Book to your library
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities;
//author : Skye, Revan, Daniela

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.BusinessLogic.AddBookLogic;


import ca.rededaniskal.BusinessLogic.UseGoogleBooksAPI;
import ca.rededaniskal.EntityClasses.Book_Instance;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;


import ca.rededaniskal.R;

/**
 * This activity lets a user input information about a book, and then adds it to their library
 * in the database.
 *
 * Todo for part 5:
 * Make the user's photo saved in the database
 */

public class Add_Book_To_Library_Activity extends AppCompatActivity {

    private static final String TAG = "Add_Book_To_Library_Activity";

    private FirebaseAuth mAuth;
    private DatabaseReference bookRef;

    //UI stuff
    private EditText addTitle, addAuthor, addISBN, addDescription;
    private Button openScanner, addBook;
    private FloatingActionButton openCamera;
    private ImageView cover;
    private String isbn;
    private String returnString;

    private AddBookLogic businessLogic;

    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);

        //Set buttons and EditTexts
        addTitle = findViewById(R.id.addTitle);
        addAuthor = findViewById(R.id.addAuthor);
        addISBN = findViewById(R.id.addISBN);

        openScanner = findViewById(R.id.openScanner);
        openCamera = findViewById(R.id.openCamera);
        addBook = findViewById(R.id.addBook);

        cover = findViewById(R.id.BookCover);

        openScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Barcode_Scanner_Activity.class);
                startActivityForResult(intent, 1);
            }
        });

        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //on click, gets info from the edittext field, validates them in AddBookLogic
                // calls addBookInstance() which creates the database object to add the book
                //Once the book is added, its details are passed to View_My_Library, and the
                // view is refreshed
                getInfo();
                validateFields();
                Book_Instance book = addBookInstance();

                Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);
                //intent.putExtra("book", book);
                startActivity(intent);
                //getParent().finish();
                finish();

            }
        };

        addBook.setOnClickListener(onClickListener);

    }

    public void getInfo() {
        String Title = addTitle.getText().toString();
        String Author = addAuthor.getText().toString();
        String ISBN = addISBN.getText().toString();

        businessLogic = new AddBookLogic(Title, Author, ISBN);
    }

    public void validateFields() {

        //Currently raises no errors, businessLogic is always Valid
        //TODO: implement this when testing stages are done for basic functionality
        String error = businessLogic.validateTitle();
        if (!error.equals("")){
            addTitle.setError(error);

        }
        String error1 = businessLogic.validateAuthor();
        if (!error1.equals("")){
            addAuthor.setError(error1);

        }
    String error2 = businessLogic.validateISBN();
        if (!error2.equals("")){
            addISBN.setError(error2);
        }
    }

    //This adds the books to the database.

    public Book_Instance addBookInstance() {

        if (businessLogic.isValid()) {

            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            String Title = addTitle.getText().toString();
            String Author = addAuthor.getText().toString();
            String ISBN = addISBN.getText().toString();

            Book_Instance bookInstance = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "a");
            AddBookDb db = new AddBookDb();
            String id = db.addBookToDatabase(bookInstance);
            bookInstance.setBookID(id);
            return bookInstance;

        }
        return null;
    }

    //Code From https://stackoverflow.com/a/5991757
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cover.setImageBitmap(photo);
        }
        else if (requestCode == 1 && resultCode == Activity.RESULT_OK){
            isbn = data.getStringExtra("ISBN");
            new UseGoogleBooksAPI(this, addTitle, addAuthor).execute(isbn);
            addISBN.setText(isbn);
        }
    }

//-------------------EMBEDDED DATABASE CLASS----------------//
    //TODO: Improve encapsulation?

private class AddBookDb {

    FirebaseDatabase db;
    DatabaseReference bookRef;
    String success;

    public AddBookDb() {
        //Creates a new reference to the correct path in the Firebase
        //Book instances are stored under there unique id, under my-books,
        //under unique user Uid, under book-instnces.

        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.db = FirebaseDatabase.getInstance();
        this.bookRef = db.getReference().child("book-instances")
                .child(user)
        .child("my-books");

    }

    public String addBookToDatabase(Book_Instance bookInstance) throws NullPointerException{


        success =bookRef.push().getKey();
        bookInstance.setBookID(success);
        Log.d(TAG, "***********---->" +bookInstance.getBookID());

        DatabaseReference m = FirebaseDatabase.getInstance().getReference("all_books");
        //Gets key and sets unique book id;

        String key = m.push().getKey();
        m.child(key).setValue(bookInstance);
        //Stores value
        //TODO: update master-book

        if (bookRef.child(success).setValue(bookInstance).isSuccessful()){
            return success;

        }
        else return null;





    }


}
}