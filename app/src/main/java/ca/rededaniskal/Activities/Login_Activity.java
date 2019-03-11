/* TYPE:
 * Activity
 *
 * PURPOSE:
 * Log in to the app
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.BusinessLogic.Log_In_Logic;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;

public class Login_Activity extends AppCompatActivity {
    private Button loginButton;
    private Button RegisterButton;
    private EditText email;
    private EditText password;
    private Log_In_Logic logic;
    private Login_Activity.SignInDB db;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Set the buttons and Edit Texts
        loginButton = (Button) findViewById(R.id.button);
        RegisterButton = (Button) findViewById(R.id.button2);
        email  = (EditText) findViewById(R.id.editText5);
        password = (EditText)findViewById(R.id.editText6);
        logo = (ImageView) findViewById(R.id.Logo);

        logo.setImageResource(R.mipmap.ic_launcher);

        //Set on click listeners
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
                finalPass();
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


    private void validateFields(){
        String em = email.getText().toString();
        String pass = password.getText().toString();
        logic = new Log_In_Logic(em, pass);

        String error = logic.validatePassword();
        if(!error.equals("")){
            password.setError(error);
        }

        String error1 = logic.validateEmail();
        if(!error1.equals("")){
            email.setError(error1);
        }
    }


    public void finalPass() {
        if (logic.isValid()) {
            db = new Login_Activity.SignInDB();
            String em = email.getText().toString();
            String pass = password.getText().toString();
            Log.d(TAG, "*********----->"+pass);
            db.signInUser(em, pass);
        }
    }


    public void nextActivity(){

        startActivity(new Intent(Login_Activity.this,Main_Activity.class));
    }


//    Enclosed database class
    public class SignInDB {

        // To read or write from the database, a database reference is needed
        private DatabaseReference mDatabase;
        private FirebaseAuth mAuth;
        private boolean success;
        private FirebaseUser newUser;

        public SignInDB(){
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

        public void signInUser(String email, String password){

            Log.d(TAG, "*********-----> IN signInUser");
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login_Activity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(getApplicationContext(), "Sign In Success", Toast.LENGTH_SHORT).show();
                                nextActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                updateUI(null);
                                success = false;
                                Toast.makeText(getApplicationContext(), "Sign In Failed", Toast.LENGTH_SHORT).show();
                            }
                            Log.d(TAG, "*********----->COMPLETED");
                        }
                    });

        }

        public FirebaseUser getNewUser() {
            return newUser;
        }


        private void setNewUser(FirebaseUser newUser) {
            this.newUser = newUser;
        }
    }
}
