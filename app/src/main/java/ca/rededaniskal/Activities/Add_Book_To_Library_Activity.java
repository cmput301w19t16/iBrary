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
import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;


//import ca.rededaniskal.BusinessLogic.AddBookLogic;


import ca.rededaniskal.BusinessLogic.Title_Author_GoogleBooksAPI;

import ca.rededaniskal.BusinessLogic.ValidateBookLogic;

import ca.rededaniskal.BusinessLogic.myCallBackString;
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

    private static final int UPLOAD_REQUEST_BI = 2000;
    private static final int UPLOAD_REQUEST_MB = 3000;
    private static final int TEST_REQUEST = 4000;

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
                bi = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "Available");

                /*mcbstr = new myCallBackString() {
                    @Override
                    public void onCallback(String url) {
                       bi.setCover(url);
                    }
                };*/

                /*
                Intent i = new Intent(v.getContext(), Photos.class);
                i.putExtra("BookInstance", bi);
                startActivity(i);

                String book_instance_url;*/

                Intent testintent = new Intent(v.getContext(), Test_Activity.class);
                startActivityForResult(testintent, TEST_REQUEST);
                Intent uploadIntent = new Intent(v.getContext(), Upload_Img_Activity.class);
                uploadIntent.putExtra("Title", Title);
                uploadIntent.putExtra("ID", bi.getBookID());
                uploadIntent.putExtra("Mode",  1);
                ByteArrayOutputStream bStream = new ByteArrayOutputStream();

                if (personalCover){
                    myCover.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    uploadIntent.putExtra("Bitmap", byteArray);
                    Log.i("About to start new activity", "have personal cover");
                    startActivityForResult(uploadIntent, UPLOAD_REQUEST_BI);
                     new Photos().BitmapToURLBI(myCover, bi);
                }
                else {
                    googleCover.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    uploadIntent.putExtra("Bitmap", byteArray);

                    startActivityForResult(uploadIntent, UPLOAD_REQUEST_BI);
                    new Photos().BitmapToURLBI(googleCover, bi);
                }

                //bi.setCover(book_instance_url);
                //new Photos().BitmapToURLMB(googleCover, Title, ISBN, mcbstr);

                if(googleCover != null){
                    Intent mbCoverIntent = new Intent(v.getContext(), Upload_Img_Activity.class);
                    mbCoverIntent.putExtra("Title", Title);
                    mbCoverIntent.putExtra("ISBN", ISBN);

                    ByteArrayOutputStream mbStream = new ByteArrayOutputStream();
                    googleCover.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = mbStream.toByteArray();
                    mbCoverIntent.putExtra("Bitmap", byteArray);

                    mbCoverIntent.putExtra("Mode",  2);
                    startActivityForResult(mbCoverIntent, UPLOAD_REQUEST_MB);
                }


                if (businessLogic.isValid().equals("")) {
                    businessLogic.saveInformation(bi, coverURLMb);
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

        else if (requestCode == UPLOAD_REQUEST_BI && resultCode == Activity.RESULT_OK){
            String URL = data.getStringExtra("URL");
            bi.setCover(URL);
        }

        else if (requestCode == UPLOAD_REQUEST_MB && resultCode == Activity.RESULT_OK){
            coverURLMb = data.getStringExtra("URL");
        }
        else if (requestCode == TEST_REQUEST && resultCode == Activity.RESULT_OK){
            Log.i("return test ", "returned from test");
        }

    }

/*
    public void BitmapToURLBI (final Book_Instance bi){
        storageReference = FirebaseStorage.getInstance().getReference();
        images = storageReference.child("images");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myCover.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] dataArray = bytes.toByteArray();
        String fileName = bi.getTitle()+ bi.getBookID();

        StorageReference imageRef = images.child(fileName + ".jpeg");

        UploadTask uploadTask = imageRef.putBytes(dataArray);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                while(!uri.isComplete());
                Uri uriURL = uri.getResult();
                bi.setCover(uriURL.toString());
                /*Toast.makeText(Add_Book_To_Library_Activity.this, "Upload Success, download URL " +
                        uriURL.toString(), Toast.LENGTH_LONG).show();
                Log.i("FBApp1 URL ", uriURL.toString());
            }
        });
    }*/
/*
    private void UploadImage(){
        myStorage = FirebaseStorage.getInstance().getReference();
        StorageReference storageReferencecover = myStorage.child("images/coverbook.jpg");


        Bitmap bitmap = ((BitmapDrawable) cover.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageReferencecover.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }*/
}


