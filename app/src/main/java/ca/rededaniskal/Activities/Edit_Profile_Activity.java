package ca.rededaniskal.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ca.rededaniskal.R;

public class Edit_Profile_Activity extends AppCompatActivity {
    Button saveButton;
    Button editProfilePicture;
    EditText username;
    boolean isValid = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);

        saveButton = (Button) findViewById(R.id.saveButton);
        editProfilePicture = (Button) findViewById(R.id.EditProfilePic);

        View includedLayout = findViewById(R.id.included);
        username = (EditText)includedLayout.findViewById(R.id.new_username);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB stuff here
                if((username.getText().toString()).matches("")) {
                    username.setError("Please enter a username");
                } else {
                    finish();
                }
            }
        });

        editProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Call new Activities from here
            }
        });
    }

}
