/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Signup for app
 *
 * ISSUES:
 *
 */package ca.rededaniskal.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;
import ca.rededaniskal.BusinessLogic.SignUpLogic;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class Signup_Activity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmText;
    private EditText emailText;
    private EditText phoneText;
    private SignUpLogic businessLogic;
    private User user;

    private FusedLocationProviderClient client;
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button button = findViewById(R.id.button_signup);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB add user to database
                getInfo();
                validateFields();
                finalPass();
            }
        });

        location = findViewById(R.id.location);

        client = LocationServices.getFusedLocationProviderClient(this);

        requestPermission();

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(Signup_Activity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(Signup_Activity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location2) {
                        if(location2!=null) {
                            //newLocation.setText(location.getLatitude() + " " + location.getLongitude());
                            location.setText(getAddressName(location2.getLatitude(), location2.getLongitude()));
                        }
                    }
                });
            }
        });
    }

    private String getAddressName(double lat, double lon) {
        String address = "";
        Geocoder geocoder = new Geocoder(Signup_Activity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lon, 1);
            address = addresses.get(0).getLocality() + ", " + addresses.get(0).getAdminArea();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION}, 1);

    }


    public void validateFields(){
        String error = businessLogic.validdatePhone();
        if(!error.equals("")){
            phoneText.setError(error);
        }
        String error1 = businessLogic.validateEmail();
        if(!error1.equals("")){
            emailText.setError(error1);
        }
        String error2 = businessLogic.validatePassword();
        if(!error2.equals("")){
            passwordText.setError(error2);
        }
        String error3 = businessLogic.validateConfirm();
        if(!error3.equals("")){
            confirmText.setError(error3);
        }
        String error4 = businessLogic.validateUsername();
        if(!error4.equals("")){
            usernameText.setError(error4);
        }
    }


    public void getInfo(){
        usernameText = findViewById(R.id.input_username);
        passwordText = findViewById(R.id.input_password);
        confirmText = findViewById(R.id.input_confirm_password);
        emailText = findViewById(R.id.input_email);
        phoneText = findViewById(R.id.input_phone);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirm = confirmText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();

        user = new User(username, email, phone, "");
        businessLogic = new SignUpLogic(username, password, confirm, email, phone);

    }


    public void finalPass(){
        if (businessLogic.isValid()){
            SignUpDB db = new SignUpDB();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            db.createUser(email, password);

            }
    }


    public void nextActivity(){
        startActivity(new Intent(Signup_Activity.this,Main_Activity.class));
    }

    //--------- SIGNUPDB ENCLOSED CLASS ------------ //

    public class SignUpDB {

        // To read or write from the database, a database reference is needed
        private DatabaseReference mDatabase;
        private FirebaseAuth mAuth;
        private boolean success;
        private FirebaseUser newUser;


        public SignUpDB(){
            mDatabase = FirebaseDatabase.getInstance().getReference();

            // Initialize FirebaseAuth
            mAuth = FirebaseAuth.getInstance();
            success = false;
        }


        public boolean isSuccess() {
            return success;
        }


        public void setSuccess(boolean success) {
            this.success = success;
        }


        public void createUser(String email, String password){
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Signup_Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                setUser();
                                nextActivity();
                                //updateUI(user);
                                success = true;
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                            updateUI(null);
                            }
                        }
                    });
        }


        public void setUser() {
            Log.d(TAG, "IN setUser");
            Log.d(TAG, user.getEmail());
            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            String key = mDatabase.push().getKey();
            mDatabase.child(key).setValue(user);

        }
    }
}
