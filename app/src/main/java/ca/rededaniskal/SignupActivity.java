package ca.rededaniskal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameText;
    private EditText passwordText;
    private EditText confirmText;
    private EditText emailText;
    private EditText phoneText;
    private SignUpLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button button = findViewById(R.id.button_signup);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
                validateFields();
                finalPass();
            }
        });
    }


    public void validateFields(){
        String error = businessLogic.validdatePhone();
        if(!error.equals("")){
            phoneText.setError(error);
        }
        String error1 = businessLogic.validateEmail();
        if(!error1.equals("")){
            emailText.setError(error);
        }
        String error2 = businessLogic.validatePassword();
        if(!error2.equals("")){
            passwordText.setError(error);
        }
        String error3 = businessLogic.validateConfirm();
        if(!error3.equals("")){
            confirmText.setError(error);
        }
        String error4 = businessLogic.validateUsername();
        if(!error.equals("")){
            usernameText.setError(error);
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

        businessLogic = new SignUpLogic(username, password, confirm, email, phone);

    }

    public void finalPass(){
        if (businessLogic.isValid()){
            // Pass to new intent
        }
    }



}
