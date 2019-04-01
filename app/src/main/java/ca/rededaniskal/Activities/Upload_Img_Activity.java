package ca.rededaniskal.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import ca.rededaniskal.BusinessLogic.myCallBackString;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.R;

/**
 * This activity uploads the image to firebase
 */

public class Upload_Img_Activity extends AppCompatActivity {

    private StorageReference storageReference;
    private StorageReference images;
    private Bitmap cover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("enter", "enter upload img");
        setContentView(R.layout.activity_upload_img);

        storageReference = FirebaseStorage.getInstance().getReference();
        images = storageReference.child("images");

        Intent intent = getIntent();
        byte[] byteArray = getIntent().getByteArrayExtra("Bitmap");
        cover = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        int mode = getIntent().getIntExtra("Mode", -1);
        Log.d("mode", Integer.toString(mode));
        String title = getIntent().getStringExtra("Title");
        String newTitle = title.replace(" ", "");

        if (mode == 1){
            Log.i("Int", "Got int extra");
            String id = intent.getStringExtra("ID");
            BitmapToURLBI(cover, newTitle, id);
        }
        else{
            String ISBN = intent.getStringExtra("ISBN");
            BitmapToURLMB(cover, newTitle, ISBN);
        }
    }

    public void BitmapToURLBI (Bitmap cover, String title, String id ){
        Log.i("Hi", "in bitmaptoURLBI");
        if(cover != null){
            storageReference = FirebaseStorage.getInstance().getReference();
            images = storageReference.child("images");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            cover.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] dataArray = bytes.toByteArray();

            String fileName = title + id;

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
                    Log.i("FBApp1 URL ", uriURL.toString());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("URL",uriURL.toString());
                    setResult(RESULT_OK,returnIntent);
                    finish();
                    //bi.setCover(uriURL.toString());
                /*Toast.makeText(Add_Book_To_Library_Activity.this, "Upload Success, download URL " +
                        uriURL.toString(), Toast.LENGTH_LONG).show();*/
                }
            });
        }
    }


    public void BitmapToURLMB (Bitmap cover, String title, String isbn){
        if (cover != null){
            storageReference = FirebaseStorage.getInstance().getReference();
            images = storageReference.child("images");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            cover.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] dataArray = bytes.toByteArray();
            String fileName = title+ isbn;

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

                    Log.i("FBApp1 URL ", uriURL.toString());

                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("URL",uriURL.toString());
                    setResult(RESULT_OK,returnIntent);
                    finish();
                }
            });
        }
    }

}
