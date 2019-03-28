/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Edit a book you own
 *
 * ISSUES:
 * Needs DB support
 * Needs to save image to DB
 * Needs to have scanner communicate with DB and this activity
 *
 */
package ca.rededaniskal.Activities;
/*author: Skye, Revan*/

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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

import ca.rededaniskal.BusinessLogic.ValidateBookLogic;
import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.R;

/**
 * This activity lets the user edit the information of a book that they own. They can change the
 * information, or delete the book.
 */


public class Edit_Book_Instance_Activity extends AppCompatActivity {
    public static final String CAMERA_PERMISSION_GRANTED = "camera permission granted";
    private static final String TAG = "Add_Book_To_Library_Activity";


    //UI stuff
    private EditText editTitle, editAuthor, editISBN, editDescription;
    private Button openScanner, save, delete;
    private FloatingActionButton openCamera;
    private ImageView cover;

    private ValidateBookLogic businessLogic;

    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book_instance);

        Intent intent = getIntent();
        final Book_Instance book = (Book_Instance) intent.getSerializableExtra("book");

        //Set buttons and EditTexts
        editTitle = findViewById(R.id.editTitle);
        editAuthor = findViewById(R.id.editAuthor);
        editISBN = findViewById(R.id.editISBN);

        openScanner = findViewById(R.id.openScanner);
        openCamera = findViewById(R.id.openCamera);
        save = findViewById(R.id.save);
        delete = findViewById(R.id.delete);

        cover = findViewById(R.id.BookCover);

        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editISBN.setText(book.getISBN());

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


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getInfo();

                editBookInstance(book);

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();

                businessLogic.delete(book.getBookID(), book.getISBN());

                Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);
                startActivity(intent);
                //getParent().finish();
                finish();
            }
        });
    }

    public void getInfo() {
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String Title = editTitle.getText().toString();
        String Author = editAuthor.getText().toString();
        String ISBN = editISBN.getText().toString();
        Bitmap bm=((BitmapDrawable)cover.getDrawable()).getBitmap();

        businessLogic = new ValidateBookLogic(Title,Author,ISBN,null, bm);

    }



    public void editBookInstance(Book_Instance book) {
        if (businessLogic.isValid().equals("")) {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


            String Title = editTitle.getText().toString();
            String Author = editAuthor.getText().toString();
            String ISBN = editISBN.getText().toString();

            Book_Instance bookInstance =
                    new Book_Instance(Title, Author, ISBN, userID,book.getPossessor(), book.getCondition(), book.getStatus());
            bookInstance.setBookID(book.getBookID());

            businessLogic.updateInformation(bookInstance);
            //getParent().finish();
            finish();
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