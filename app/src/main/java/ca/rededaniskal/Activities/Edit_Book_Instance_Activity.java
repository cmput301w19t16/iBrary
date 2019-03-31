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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import ca.rededaniskal.BusinessLogic.LoadImage;
import ca.rededaniskal.BusinessLogic.ValidateBookLogic;
import ca.rededaniskal.Barcode.Barcode_Scanner_Activity;
import ca.rededaniskal.Database.Photos;
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
    private FloatingActionButton openCamera, removeCover;
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
        removeCover = findViewById(R.id.removePic);

        cover = findViewById(R.id.BookCover);

        editTitle.setText(book.getTitle());
        editAuthor.setText(book.getAuthor());
        editISBN.setText(book.getISBN());

        if(book.getCover() != null || book.getCover() != ""){
            LoadImage loader = new LoadImage(cover);
            loader.execute(book.getCover());
        }

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

                businessLogic.delete(book.getBookID());

                Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);
                startActivity(intent);
                //getParent().finish();
                finish();
            }
        });
        removeCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable book_icon = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_book_black_24dp, null);
                cover.setImageDrawable(book_icon);

                Bitmap newCover = drawableToBitmap(book_icon);

                Photos photos = new Photos();
                photos.bitmapToURLBI(newCover, book);
            }
        });
    }

    public void getInfo() {
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String Title = editTitle.getText().toString();
        String Author = editAuthor.getText().toString();
        String ISBN = editISBN.getText().toString();

       /* BitmapDrawable drawable = (BitmapDrawable) cover.getDrawable();
        Bitmap newCover = drawable.getBitmap();
        Photos photos = new Photos();
        photos.bitmapToURLBI(newCover, book);*/

        businessLogic = new ValidateBookLogic(Title,Author,ISBN, getApplicationContext());
    }



    public void editBookInstance(Book_Instance book) {
        if (businessLogic.isValid().equals("")) {
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();


            String Title = editTitle.getText().toString();
            String Author = editAuthor.getText().toString();
            String ISBN = editISBN.getText().toString();

            Book_Instance bookInstance =
                    new Book_Instance(Title, Author, ISBN, userID,book.getPossessor(), book.getCondition(), book.getStatus(), null);

            BitmapDrawable drawable = (BitmapDrawable) cover.getDrawable();
            Bitmap newCover = drawable.getBitmap();
            Photos photos = new Photos();
            photos.bitmapToURLBI(newCover, bookInstance);

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

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }




}