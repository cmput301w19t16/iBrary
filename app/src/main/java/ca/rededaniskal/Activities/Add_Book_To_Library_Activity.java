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
//author : Skye, Revan
//Tutorial firebase image upload: https://www.youtube.com/watch?v=Zy2DKo0v-OY
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import ca.rededaniskal.BuildConfig;
import ca.rededaniskal.BusinessLogic.AddBookLogic;


import ca.rededaniskal.Database.AddBookDb;
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

public class Add_Book_To_Library_Activity extends AppCompatActivity implements Serializable {

    private static final String TAG = "Add_Book_To_Library_Activity";


    //UI stuff
    private EditText addTitle, addAuthor, addISBN, addDescription;
    private Button openScanner, addBook;
    private FloatingActionButton openCamera;
    private ImageView cover;


    private AddBookLogic businessLogic;

    private StorageReference myStorage;
    private ProgressDialog myProgress;
    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private Uri picUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);

        myStorage = FirebaseStorage.getInstance().getReference();

        //Set buttons and EditTexts
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


        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        File newdir = new File(dir);
        if (!newdir.exists()) {
            newdir.mkdir();
        }
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

                String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                String Title = addTitle.getText().toString();
                String Author = addAuthor.getText().toString();
                String ISBN = addISBN.getText().toString();
                businessLogic = new AddBookLogic(Title, Author, ISBN);


                if (businessLogic.isValid().equals("")) {
                    businessLogic.saveInformation(new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "Available"));
                    Intent intent = new Intent(v.getContext(), View_My_Library_Activity.class);


                    startActivity(intent);

                    finish();
                } else {


                }


            }
        };

        addBook.setOnClickListener(onClickListener);

    }

    public void getInfo() {
        String Title = addTitle.getText().toString();
        String Author = addAuthor.getText().toString();
        String ISBN = addISBN.getText().toString();


    }

    private void uploadImage() {
        if (picUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = myStorage.child("images/" + UUID.randomUUID().toString());
            ref.putFile(picUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(Add_Book_To_Library_Activity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Add_Book_To_Library_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
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

    /**
     * Create a File for saving an image
     */
    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyApplication");

        /**Create the storage directory if it does not exist*/
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        /**Create a media file name*/
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".png");
        } else {
            return null;
        }

        return mediaFile;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cover.setImageBitmap(photo);
            uploadImage(photo);
            //Uri uri = data.getData();
            //cover.setImageURI(uri);

           /* StorageReference imageStorage = myStorage.child("BookCover").child(uri.getLastPathSegment());
            imageStorage.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSucess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Add_Book_To_Library_Activity.this, "Uploaded...", Toast.LENGTH_SHORT);
                }

            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Add_Book_To_Library_Activity.this, "Not Uploaded...", Toast.LENGTH_LONG);
                        }
                    });
        }


    /*
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataByte = baos.toByteArray();

                UploadTask uploadTask = mountainsRef.putBytes(data);
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

            }
*/




        /*launchMediaScanIntent();
        final Bitmap photo = (Bitmap) data.getExtras().get("data");

        StorageReference filepath = myStorage.child("photos").child(picUri.getLastPathSegment());
        filepath.putFile(picUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                cover.setImageBitmap(photo);
            }
        });/*

                /*
                if (picUri != null) {
                    String path1 = picUri.getPath();
                    if (path1 != null) {
                        File file1 = new File(path1);
                        Uri capturedUri = Uri.fromFile(file1);//here you get the URI
                        //you can easily get the path from URI if you need
                    }
                }*/





        /*if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri uri = picUri;
            String strUri = getIntent().getStringExtra("Uri");
            uri = Uri.parse(strUri);
            cover.setImageURI(uri);
            Bitmap photo = (Bitmap) data.getExtras().get("data");*/


            //myProgress.setMessage("Uploading Image ...");
            //myProgress.show();

            /*final StorageReference filepath = myStorage.child("Photos").child(picUri.getLastPathSegment());

            filepath.putFile(picUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final Uri downloadUrl = uri;


                        }
                    });
                }
            });*/
            //cover.setImageBitmap(photo);
            /*
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    myProgress.dismiss();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    Picasso.with(Add_Book_To_Library_Activity.this).load(downloadUri).fit().centerCrop().into(cover);


                    Toast.makeText(Add_Book_To_Library_Activity.this, "Uploading Successful!", Toast.LENGTH_SHORT).show();
                    myProgress.dismiss();
                }
            });*/
        }
            else if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                String ISBN = data.getStringExtra("ISBN");
                addISBN.setText(ISBN);
            }

    }

/*
    private void storeImageToFirebase() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8; // shrink it down otherwise we will use stupid amounts of memory
        Bitmap bitmap = BitmapFactory.decodeFile(picUri.getPath(), options);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        String base64Image = Base64.encodeToString(bytes, Base64.DEFAULT);

        // we finally have our base64 string version of the image, save it.
        myStorage.child("pic").setValue(base64Image);
        System.out.println("Stored image with length: " + bytes.length);
    }
    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(picUri);
        this.sendBroadcast(mediaScanIntent);
    }*/


    private void uploadImage(Bitmap bitmap) {
        Random random = new Random();
        int key =random.nextInt(1000);
        myProgress.show();
        final StorageReference ref = myStorage.child("drivers/" + key + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] data = baos.toByteArray();

        final UploadTask uploadTask = ref.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                myProgress.dismiss();
                Toast.makeText(Add_Book_To_Library_Activity.this, "Uploaded", Toast.LENGTH_SHORT).show();

                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downUri = task.getResult();
                            Log.d("Final URL", "onComplete: Url: " + downUri.toString());
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myProgress.dismiss();
                Toast.makeText(Add_Book_To_Library_Activity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


