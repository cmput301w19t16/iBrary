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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;

import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;

import java.io.Serializable;


//import ca.rededaniskal.BusinessLogic.AddBookLogic;


import ca.rededaniskal.BusinessLogic.Title_Author_GoogleBooksAPI;

import ca.rededaniskal.BusinessLogic.ValidateBookLogic;

import ca.rededaniskal.BusinessLogic.myCallBackString;
import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.BusinessLogic.AsyncResponse;
import ca.rededaniskal.Database.Photos;

import ca.rededaniskal.Database.Write_Post_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;


import ca.rededaniskal.EntityClasses.Post;
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
    private EditText addTitle, addAuthor, addISBN;
    private Button openScanner, addBook;
    private FloatingActionButton openCamera;
    private ImageView cover;

    private String returnString;
    private String Title;
    private String Author;
    private String ISBN;
    private String titleHint;
    private String authorHint;
    private String isbnHint;

    private ValidateBookLogic businessLogic;

    private StorageReference myStorage;
    private ProgressDialog myProgress;
    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private static final int UPLOAD_REQUEST_BI = 2000;
    private static final int UPLOAD_REQUEST_MB = 3000;

    private Bitmap myCover = null;
    private myCallbackBookInstance mcbi;
    private Title_Author_GoogleBooksAPI asyncTask;
    private Bitmap googleCover;
    private boolean alreadyGotBookInfoAPI = false;
    private boolean personalCover = false;
    private Book_Instance bi;
    private Uri uri;
    private StorageReference storageReference;
    private StorageReference images;
    private myCallBackString mcbstr;
    private String coverURLMb;
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

        addAuthor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    authorHint = "";
                }


            }
        });
        addISBN.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    isbnHint = "";
                }

            }
        });
        addTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    titleHint = "";
                }
            }
        });

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
                getInfo();
                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (alreadyGotBookInfoAPI == false) {
                    asyncTask.execute(ISBN);
                }

                businessLogic = new ValidateBookLogic(Title, Author, ISBN, getApplicationContext());
                bi = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "Available");




                String error_m = businessLogic.isValid();
                if (error_m.equals("")) {

                    Photos photos = new Photos();
                    if (personalCover) {
                        photos.bitmapToURLBI(myCover, bi);
                    } else {
                        photos.bitmapToURLBI(googleCover, bi);
                    }
                    String url = photos.BitmapToURLMB(googleCover, Title, ISBN);

                    businessLogic.saveInformation(bi, url);
                    Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Add_Book_To_Library_Activity.this, error_m, Toast.LENGTH_SHORT);
                    authorHint = businessLogic.getAuthorError();
                    titleHint = businessLogic.getTitleError();
                    isbnHint = businessLogic.getISBNError();
                    set_Book_Info_Hints();
                }
            }
        };
        addBook.setOnClickListener(onClickListener);
    }

    public void processFinish(Bitmap output) {
        googleCover = output;
    }

    public void getInfo() {
        Title = addTitle.getText().toString();
        Author = addAuthor.getText().toString();
        ISBN = addISBN.getText().toString();


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

            myCover = (Bitmap) data.getExtras().get("data");
            personalCover = true;
            cover.setImageBitmap(myCover);

        } else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String ISBN = data.getStringExtra("ISBN");
            asyncTask.execute(ISBN);
            alreadyGotBookInfoAPI = true;
            addISBN.setText(ISBN);
        } else if (requestCode == UPLOAD_REQUEST_BI && resultCode == Activity.RESULT_OK) {
            String URL = data.getStringExtra("URL");
            bi.setCover(URL);
        } else if (requestCode == UPLOAD_REQUEST_MB && resultCode == Activity.RESULT_OK) {
            coverURLMb = data.getStringExtra("URL");
        }


    }

    public void set_Book_Info_Hints() {
        addAuthor.setHint(authorHint);
        addTitle.setHint(titleHint);
        addISBN.setHint(isbnHint);
    }
}