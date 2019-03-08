/*
 *
 * Created by Delaney on 07/03/2019
 * Handles the database of SignUpLogic
 *
 * */


package ca.rededaniskal.Database;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

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
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            setNewUser(user);
                            success = true;
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            success = false;

//                            TODO: Create error to send to front if user creation was an error

                        }
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
