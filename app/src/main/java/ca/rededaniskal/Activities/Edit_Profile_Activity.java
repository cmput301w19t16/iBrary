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

import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

//Author: RevaN
public class Edit_Profile_Activity extends AppCompatActivity {
    Button saveButton;
    FloatingActionButton editProfilePic;
    ImageView profilePicture;
    EditText newUsername, newPhone, newEmail, newLocation,
            oldPassword, newPassword, confirmNewPassword;
    private editUserDetailsDB db;

    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

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

        oldPassword = findViewById(R.id.old_pass);
        newPassword = findViewById(R.id.new_pass);
        confirmNewPassword = findViewById(R.id.confirm_pass);
        db = new editUserDetailsDB();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB push changes to DB

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePicture.setImageBitmap(photo);
        }
    }


    private void returnToLogin() {
        startActivity(new Intent(this, Login_Activity.class));
    }


    public class editUserDetailsDB{
        private FirebaseAuth mAuth;
        private String email;
        private String username;
        private String location;
        private String phone;
        private FirebaseUser user;
        private DatabaseReference mDatabase;
        private List<User> userList;


        public editUserDetailsDB() {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            userList = new ArrayList<>();
            if (user != null) {
                email = user.getEmail();
                getUserDetails();

            } else {
                returnToLogin();
            }
        }


        private void saveNewDetails(){
        }


        private void getUserDetails(){
            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("email")
                    .equalTo(email);

            Log.d(TAG, "*********----->"+email);
            query.addListenerForSingleValueEvent(valueEventListener);
        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                Log.d(TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.d(TAG, "*********----->"+snapshot.getValue());
                        User user = snapshot.getValue(User.class);
                        setTexts(user.getEmail(), user.getPhoneNumber(), user.getLocation(), user.getUserName());
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }
}
