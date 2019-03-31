package ca.rededaniskal.Database;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;
import java.util.UUID;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;
import ca.rededaniskal.BusinessLogic.myCallBackMasterBook;
import ca.rededaniskal.BusinessLogic.myCallBackString;
import ca.rededaniskal.BusinessLogic.myCallbackBookInstance;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Master_Book;


public class Photos {


    static private DatabaseReference myStorage;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    static private ProgressDialog myProgress;
    static private Context context;

    private StorageReference storageReference;
    private StorageReference images;

    public Photos() {
        //myStorage = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        images = storageReference.child("images");
        //myProgress = new ProgressDialog(context);

    }

    public void bitmapToURLBI(Bitmap cover, final Book_Instance bi) {
        if (cover != null) {
            storageReference = FirebaseStorage.getInstance().getReference();
            images = storageReference.child("images");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            cover.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] dataArray = bytes.toByteArray();
            String title = bi.getTitle().replace(" ", "");
            String fileName = title + bi.getBookID();

            StorageReference imageRef = images.child(fileName + ".jpeg");

            UploadTask uploadTask = imageRef.putBytes(dataArray);


            /*uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete()) ;
                    Uri uriURL = uri.getResult();
                    Log.i("FBApp1 URL ", uriURL.toString());
                    bi.setCover(uriURL.toString());
//                    mcbstr.onCallback(uriURL.toString());
                    //bi.setCover(uriURL.toString());
                /*Toast.makeText(Add_Book_To_Library_Activity.this, "Upload Success, download URL " +
                        uriURL.toString(), Toast.LENGTH_LONG).show();
                }
            });*/
            while (!uploadTask.isComplete()) ;
            Task<Uri> uri = uploadTask.getResult().getStorage().getDownloadUrl();
            while (!uri.isComplete()) ;
            Uri uriURL = uri.getResult();
            Log.i("FBApp1 URL ", uriURL.toString());
            bi.setCover(uriURL.toString());


        }
    }

    public String BitmapToURLMB(Bitmap cover, String title, String isbn) {
        if (cover != null) {
            storageReference = FirebaseStorage.getInstance().getReference();
            images = storageReference.child("images");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            cover.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] dataArray = bytes.toByteArray();
            String newTitle = title.replace(" ", "");
            String fileName = newTitle + isbn;

            StorageReference imageRef = images.child(fileName + ".jpeg");

            UploadTask uploadTask = imageRef.putBytes(dataArray);


            /*uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uri.isComplete()) ;
                    Uri uriURL = uri.getResult();

                    Log.i("FBApp1 URL ", uriURL.toString());

                    //mcbstr.onCallback(uriURL.toString());

                }
            });*/
            while (!uploadTask.isComplete()) ;
            Task<Uri> uri = uploadTask.getResult().getStorage().getDownloadUrl();
            while (!uri.isComplete()) ;
            Uri uriURL = uri.getResult();
            Log.i("Masterbook URL ", uriURL.toString());
            return (uriURL.toString());
        }
        return "";
    }

    /*

    private void uploadToFirebase() {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        //Select destination filename, folder
        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        final StorageReference profileimageRef = storageRef.child("profilepictures/" + System.currentTimeMillis() + ".jpg");
        UploadTask uploadTask = profileimageRef.putFile(uriProfileImage);

        Log.wtf("ImageURL", profileimageRef.toString());

        //Upload image
        if(uriProfileImage != null)
        {
            //Show progressbar
            progressBar.setVisibility(View.VISIBLE);

            Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }

                    return profileimageRef.getDownloadUrl();
                }

                */
/*

    final StorageReference ref = myStorage.child("ur path ");
    uploadTask = ref.putFile(file);

    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
        @Override
        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            return ref.getDownloadUrl(); //Call the getDownloadUrl() method
        }
    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
        @Override
        public void onComplete(@NonNull Task<Uri> task) {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
            } else {
                // Handle failures
            }
        }
    });*/
    /*public String tryGetURL(Bitmap inImage, Book_Instance bi) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] data = bytes.toByteArray();
            String fileName = UUID.randomUUID().toString();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference images = storageReference.child("images");
            StorageReference imageRef = images.child(fileName + ".jpeg");

            UploadTask uploadTask = imageRef.putBytes(data);

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
                    Uri url = uri.getResult();

                }
            });



            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
            Uri uri = Uri.parse(path);

            String url = "http://" + bi.getTitle() + uri.toString() + bi.getBookID() + ".html";
            url = url.replace(" ", "");
            final StorageReference ImagesRef = myStorage.child(url);
            return url;
        }
        else{
            return "";
        }
    }*/

    public String returnURLStrFromBitmapBi(Bitmap inImage, Book_Instance bi) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
            Uri uri = Uri.parse(path);

            String url = "http://" + bi.getTitle() + uri.toString() + bi.getBookID() + ".html";
            url = url.replace(" ", "");
            return url;
        } else {
            return "";
        }
    }

    public String returnURLStrFromBitmapMb(Bitmap inImage, String title, String isbn) {
        if (inImage != null) {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
            Uri uri = Uri.parse(path);

            String url = "http://" + title + uri.toString() + isbn + "mb.html";
            url = url.replace(" ", "");
            return url;
        } else {
            return "";
        }
    }

    /*
        public void getURLFromBitmap(Bitmap inImage, myCallbackBookInstance mcbi, Book_Instance bi) {
            if (inImage != null) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
                Log.d("path path", path);
                Uri uri = Uri.parse(path);
                Log.d("uri uri", uri.toString());


                String url = "http://" + bi.getTitle() + uri.toString() + bi.getBookID() + ".html";
                Log.d("http uri", url);
                bi.setCover(url);
                mcbi.onCallback(bi);

            } else {
                Log.d("Bitmap", "Bitmap null");
            }
        }

        public void getURLFromBitmapMasterBook(Bitmap inImage, myCallBackMasterBook mcmb, Master_Book mb) {
            if (inImage != null){
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
                Uri uri = Uri.parse(path);


                String url = "http://" + mb.getTitle() + uri.toString() + mb.getISBN() + ".html";
                mb.setGoogleCover(url);
                mcmb.onCallback(mb);

            }
        }
    */
    //https://stackoverflow.com/questions/8992964/android-load-from-url-to-bitmap
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
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