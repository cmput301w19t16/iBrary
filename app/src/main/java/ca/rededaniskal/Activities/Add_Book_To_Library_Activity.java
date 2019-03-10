package ca.rededaniskal.Activities;

/*author : Skye*/

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import ca.rededaniskal.BusinessLogic.AddBookLogic;
import ca.rededaniskal.Database.AddBookDb;
import ca.rededaniskal.EntityClasses.Book_Instance;

import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;

import ca.rededaniskal.R;

public class Add_Book_To_Library_Activity extends AppCompatActivity {
    private static final String TAG = "Add_Book_To_Library_Activity";
    private FirebaseAuth mAuth;
    private DatabaseReference bookRef;

    //UI stuff
    private EditText addTitle, addAuthor, addISBN, addDescription;
    private Button openScanner, addBook;
    private FloatingActionButton openCamera;
    private ImageView cover;

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
                startActivity(intent);
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
                //TODO: DB
                getInfo();
                validateFields();
                addBookInstance();
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

    public void addBookInstance() {
        if (businessLogic.isValid()) {

            String userID = "del@del.com";
            String Title = addTitle.getText().toString();
            String Author = addAuthor.getText().toString();
            String ISBN = addISBN.getText().toString();

            Book_Instance bookInstance = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "a");
            AddBookDb db = new AddBookDb();
            db.addBookToDatabase(bookInstance);

            if( !businessLogic.addBookSuccess( bookInstance ).equals("")){
                Toast.makeText(this, "Book Saved!", Toast.LENGTH_SHORT);

            }
            else{
                Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT);
            }
        }
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
    }
}


