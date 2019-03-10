package ca.rededaniskal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import ca.rededaniskal.R;

public class Login_Activity extends AppCompatActivity {
    Button loginButton;
    Button RegisterButton;
    EditText name;
    EditText password;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Toolbar toolbar = findViewById(R.id.toolbar); TODO: ???

        //Set the buttons and Edit Texts
        loginButton = (Button) findViewById(R.id.button);
        RegisterButton = (Button) findViewById(R.id.button2);
        name  = (EditText) findViewById(R.id.editText5);
        password = (EditText)findViewById(R.id.editText6);
        logo = (ImageView) findViewById(R.id.Logo);

        logo.setImageResource(R.mipmap.ic_launcher);

        //Set on click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB login in only if authentication passes

                //For now lets go to the main screen
                Intent intent = new Intent(v.getContext(), Main_Activity.class);
                startActivity(intent);

            }
        });

        //Set on click listeners
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Signup_Activity.class);
                startActivity(intent);
            }
        });
    }
}
