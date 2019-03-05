package ca.rededaniskal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button button = (Button) findViewById(R.id.button_signup);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        if(!validate()) {
            Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Sign Up Complete", Toast.LENGTH_SHORT).show();
            // TODO: Add user to database and go to new activity
        }
    }

    public boolean validate() {
        boolean valid = true;

        EditText usernameText = (EditText) findViewById(R.id.input_username);
        EditText passwordText = (EditText) findViewById(R.id.input_password);
        EditText confirmText = (EditText) findViewById(R.id.input_confirm_password);
        EditText emailText = (EditText) findViewById(R.id.input_email);
        EditText phoneText = (EditText) findViewById(R.id.input_phone);

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirm = confirmText.getText().toString();
        String email = emailText.getText().toString();
        String phone = phoneText.getText().toString();

        if (username.isEmpty()) {
            usernameText.setError("Please enter username");
            valid = false;
        }
        if (password.isEmpty()) {
            passwordText.setError("Please enter a password");
            valid = false;
        }
        if (confirm.isEmpty()) {
            confirmText.setError("Please confirm the password");
            valid = false;
        } else if (!password.equals(confirm)) {
            confirmText.setError("Must be the same as password");
            valid = false;
        }
        if (email.isEmpty()) {
            emailText.setError("Please an email");
            valid = false;
        } else if (!isEmailValid(email)) {
            emailText.setError("That is not a valid email");
            valid = false;
        }
        if (phone.isEmpty()) {
            phoneText.setError("Please a phone");
            valid = false;
        } else if (phone.length() != 10) {
            phoneText.setError("That is not a valid phone number");
            valid = false;
        }

        return valid;
    }

    public boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
