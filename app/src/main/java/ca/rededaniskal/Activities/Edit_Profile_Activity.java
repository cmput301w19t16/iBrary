/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Edit you own profile
 *
 * ISSUES:
 * Needs DB Image support
 *
 */
package ca.rededaniskal.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.Activities.Fragments.View_Own_Profile_Fragment;
import ca.rededaniskal.BusinessLogic.LoadImage;
import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.BusinessLogic.myCallbackUser;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Photos;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.EntityClasses.Master_Book;

import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;
import ca.rededaniskal.Database.editUserDetailsDB;

/**
 * This activity lets a user update their personal information.
 */

//Author: RevaN
public class Edit_Profile_Activity extends AppCompatActivity {
    public static final String GET_TEXTS = "*********----->getTexts";
    Button saveButton, home;
    FloatingActionButton editProfilePic;
    ImageView profilePicture;
    EditText newUsername, newPhone, newEmail, newLocation,
            oldPassword, newPassword, confirmNewPassword;
    private editUserDetailsDB db;
    private Bitmap myBitCover;

    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
           myBitCover = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(myBitCover);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);

        profilePicture = findViewById(R.id.profile_image);

        saveButton = (Button) findViewById(R.id.saveButton);
        editProfilePic = findViewById(R.id.editProfilePic);

        newUsername = findViewById(R.id.new_username);
        newPhone = findViewById(R.id.new_phone);
        newEmail = findViewById(R.id.new_email);
        newLocation = findViewById(R.id.new_location);
        home = findViewById(R.id.home);


        oldPassword = findViewById(R.id.old_pass);
        newPassword = findViewById(R.id.new_pass);
        confirmNewPassword = findViewById(R.id.confirm_pass);
        //removePic = findViewById(R.id.removePic);

        db = new editUserDetailsDB(this);
        if (db.getFailed()){returnToLogin();}

        Users_DB usersDb = new Users_DB();

        final myCallbackUser myCallbackUser = new myCallbackUser() {
            @Override
            public void onCallback(User user) {
                String urlProfilePic = user.getProfilePic();
                if(urlProfilePic != null){
                    LoadImage loader = new LoadImage(profilePicture);
                    loader.execute(urlProfilePic);
                }
            }
        };

        BookInstanceDb bookInstanceDb = new BookInstanceDb();
        String uid = bookInstanceDb.getUID();
        usersDb.getUser(uid, myCallbackUser);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User use = getTexts();
                myCallbackBool mcbb = new myCallbackBool() {
                    @Override
                    public void onCallback(Boolean value) {
                        String toast;
                        if (value){
                            toast = "User account updated.";
                        }
                        else{
                            toast = "Sorry, that username is taken.";
                        }
                        Toast.makeText(getApplicationContext(),toast,Toast.LENGTH_SHORT).show();

                    }
                };
                db.uniqueUserName(use, mcbb);

            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(Edit_Profile_Activity.this, Main_Activity.class );
                startActivity(intent);
            }
        });


        editProfilePic.setOnClickListener(new View.OnClickListener() {
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

        /*removePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    public void userNameTaken(){
        EditText u = findViewById(R.id.new_username);
        u.setError("This username is already taken");
    }

    public User getTexts(){
        Log.d(TAG, "*********----->getTexts");

        EditText e = findViewById(R.id.new_email);
        EditText p = findViewById(R.id.new_phone);
        EditText l = findViewById(R.id.new_location);
        EditText u = findViewById(R.id.new_username);
        String email =  e.getText().toString();
        String phone = p.getText().toString();
        String location = l.getText().toString();
        String username = u.getText().toString();

        Log.d(TAG, "*********----->LEAVING getTexts");

        User user = new User(username, email, phone, location);

        Photos photos = new Photos();
        photos.bitmapToURLUser(myBitCover, user);

        return user;

    }

    public void setTexts(String email, String phone, String location, String username){
        Log.d(TAG, "*********----->setTexts");

        EditText e = findViewById(R.id.new_email);
        EditText p = findViewById(R.id.new_phone);
        EditText l = findViewById(R.id.new_location);
        EditText u = findViewById(R.id.new_username);
        Log.d(TAG, "*********----->"+email);
        e.setText(email);
        p.setText(phone);
        l.setText(location);
        u.setText(username);
        Log.d(TAG, "*********----->LEAVING setTexts");
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


    private void returnToLogin() {
        startActivity(new Intent(this, Login_Activity.class));
    }

    public void nextActivity(){
        this.finish();
    }

}
