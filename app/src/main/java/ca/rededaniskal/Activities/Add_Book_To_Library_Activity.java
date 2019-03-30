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
//Tutorial firebase image upload: https://www.youtube.com/watch?v=Zy2DKo0v-OY

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;

import java.io.Serializable;


//import ca.rededaniskal.BusinessLogic.AddBookLogic;


import ca.rededaniskal.BusinessLogic.Title_Author_GoogleBooksAPI;

import ca.rededaniskal.BusinessLogic.ValidateBookLogic;

import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.BusinessLogic.AsyncResponse;
import ca.rededaniskal.Database.Photos;
import ca.rededaniskal.EntityClasses.Book_Instance;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;


import ca.rededaniskal.R;

/**
 * This activity lets a user input information about a book, and then adds it to their library
 * in the database.
 * <p>
 * Todo for part 5:
 * Make the user's photo saved in the database
 */

public class Add_Book_To_Library_Activity extends AppCompatActivity implements Serializable, AsyncResponse {

    private static final String TAG = "Add_Book_To_Library_Activity";


    //UI stuff
    private EditText addTitle, addAuthor, addISBN, addDescription;
    private Button openScanner, addBook;
    private FloatingActionButton openCamera;
    private ImageView cover;
    private String ISBN;

    private ValidateBookLogic businessLogic;

    private StorageReference myStorage;
    private ProgressDialog myProgress;
    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private Bitmap myCover = null;
    private myCallbackBookInstance mcbi;
    private Title_Author_GoogleBooksAPI asyncTask;
    private Bitmap googleCover;
    private boolean alreadyGotBookInfoAPI = false;
    private boolean personalCover = false;
//    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);

        //Set buttons and EditTexts
        asyncTask = new Title_Author_GoogleBooksAPI(getApplicationContext(), addTitle, addAuthor);
        asyncTask.delegate = this;

        addTitle = findViewById(R.id.addTitle);
        addAuthor = findViewById(R.id.addAuthor);
        addISBN = findViewById(R.id.addISBN);

        openScanner = findViewById(R.id.openScanner);
        openCamera = findViewById(R.id.openCamera);
        addBook = findViewById(R.id.addBook);

        cover = findViewById(R.id.BookCover);

        myProgress = new ProgressDialog(this);

        openScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Barcode_Scanner_Activity.class);
                intent.putExtra("ReturnClass", Add_Book_To_Library_Activity.class);
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
            public void onClick(final View v) {
                //on click, gets info from the edittext field, validates them in ValidateBookLogic
                // calls addBookInstance() which creates the database object to add the book
                //Once the book is added, its details are passed to View_My_Library, and the
                // view is refreshed

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String Title = addTitle.getText().toString();
                String Author = addAuthor.getText().toString();
                String ISBN = addISBN.getText().toString();
                if (alreadyGotBookInfoAPI == false){
                    asyncTask.execute(ISBN);
                }

                businessLogic = new ValidateBookLogic(Title, Author, ISBN, getApplicationContext());
                Book_Instance bi = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "Available");
                String book_instance_url;
                if (personalCover){
                     book_instance_url = new Photos(getApplicationContext()).returnURLStrFromBitmapBi(myCover, bi);
                }
                else {
                     book_instance_url = new Photos(getApplicationContext()).returnURLStrFromBitmapBi(googleCover, bi);
                }

                bi.setCover(book_instance_url);
                String master_book_url = new Photos(getApplicationContext()).returnURLStrFromBitmapMb(googleCover, Title, ISBN);

                if (businessLogic.isValid().equals("")) {
                    businessLogic.saveInformation(bi, getApplicationContext(), master_book_url);
                    Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);
                    startActivity(intent);
                    finish();
                } else {


                }

/*
                mcbi = new myCallbackBookInstance() {
                    @Override
                    public void onCallback(Book_Instance book_instance) {
                        if (businessLogic.isValid().equals("")) {
                            businessLogic.saveInformation(book_instance, getApplicationContext());
                            Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);


                            startActivity(intent);

                            finish();
                        } else {


                        }
                    }
                };
                new Photos(getApplicationContext()).getURLFromBitmap(myCover, mcbi, bi);

                Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);


                startActivity(intent);

                finish();*/
            }
        };

        addBook.setOnClickListener(onClickListener);

    }

    public void processFinish(Bitmap output) {
        googleCover = output;
    }

    public void getInfo() {
        String Title = addTitle.getText().toString();
        String Author = addAuthor.getText().toString();
        String ISBN = addISBN.getText().toString();


    }

    /*
        public void onCallback(Book_Instance book_instance) {
            if (businessLogic.isValid().equals("")) {
                businessLogic.saveInformation(book_instance, getApplicationContext());
                Intent intent = new Intent(getWindow().getDecorView().getRootView().getContext(), View_My_Library_Activity.class);


                startActivity(intent);

                finish();
            } else {


            }
        }*/
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
            myCover = (Bitmap) data.getExtras().get("data");
            personalCover = true;
            cover.setImageBitmap(myCover);
        }

        else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String ISBN = data.getStringExtra("ISBN");
            asyncTask.execute(ISBN);
            alreadyGotBookInfoAPI = true;
            addISBN.setText(ISBN);
        }

    }
}


