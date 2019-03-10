package ca.rededaniskal.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import ca.rededaniskal.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Edit_Profile_Activity extends AppCompatActivity {
    Button saveButton;
    Button editProfilePicture;
    boolean isValid = true;
    FloatingActionButton editProfilePic;
    ImageView profilePicture;
    EditText newUsername, newPhone, newEmail, newLocation,
            oldPassword, newPassword, confirmNewPassword;

    //For Camera
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private FusedLocationProviderClient client;
    TextView location;

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

        location = findViewById(R.id.location);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB stuff here
                if ((newUsername.getText().toString()).matches("")) {
                    newUsername.setError("Please enter a username");
                } else {
                    finish();
                }
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

        requestPermission();

        client = LocationServices.getFusedLocationProviderClient(this);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(Edit_Profile_Activity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnSuccessListener(Edit_Profile_Activity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if(location!=null) {
                            //newLocation.setText(location.getLatitude() + " " + location.getLongitude());
                            newLocation.setText(getAddressName(location.getLatitude(), location.getLongitude()));
                        }
                    }
                });

            }
        });
    }

    private String getAddressName(double lat, double lon) {
        String address = "";
        Geocoder geocoder = new Geocoder(Edit_Profile_Activity.this, Locale.getDefault());
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
}
