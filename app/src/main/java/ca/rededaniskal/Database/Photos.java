package ca.rededaniskal.Database;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;
import ca.rededaniskal.BusinessLogic.myCallBackMasterBook;
import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;


public class Photos {


    static private StorageReference myStorage;
    static private ProgressDialog myProgress;
    static private Context context;


    public Photos(Context context) {
        myStorage = FirebaseStorage.getInstance().getReference();
        myProgress = new ProgressDialog(context);
        this.context = context;

    }


    public URL returnURLFromBitmap(Bitmap inImage, String title, String id) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "http://" + title + id + ".html", null);
            Uri uri = Uri.parse(path);

            try {
                return new URL(uri.toString());
                //mcbi.onCallback(bi);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public void getURLFromBitmap(Bitmap inImage, myCallbackBookInstance mcbi, Book_Instance bi) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
            Log.d("path path", path);
            Uri uri = Uri.parse(path);
            Log.d("uri uri", uri.toString());


            String url = "http://" + bi.getTitle() + bi.getBookID() + uri.toString() + ".html";
            Log.d("http uri", url);
            bi.setCover(url);
            mcbi.onCallback(bi);

        } else {
            Log.d("Bitmap", "Bitmap null");
        }
    }

    public void getURLFromBitmapMasterBook(Bitmap inImage, myCallBackMasterBook mcmb, Master_Book mb) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "http://TitleOriginal", null);
        Uri uri = Uri.parse(path);
        
        mb.setGoogleCover(uri.toString());
        mcmb.onCallback(mb);

    }


    /*
    //https://stackoverflow.com/questions/40581930/how-to-upload-an-image-to-firebase-storage
    //Given a bitmap, upload it to FireBase as jpg
     public URL getURLFromBitmap(Bitmap bitmap) {
        Random random = new Random();
        int key = random.nextInt(1000);
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
                Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();

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
                            //picUri = downUri;
                            Log.d("Final URL", "onComplete: Url: " + downUri.toString());
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                myProgress.dismiss();
                Toast.makeText(context, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
}