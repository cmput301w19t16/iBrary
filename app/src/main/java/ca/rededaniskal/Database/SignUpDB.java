package ca.rededaniskal.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.Activities.Signup_Activity;
import ca.rededaniskal.EntityClasses.User;

import static android.content.ContentValues.TAG;

public class SignUpDB {

    // To read or write from the database, a database reference is needed
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private boolean success;
    private FirebaseUser newUser;
    private Signup_Activity parent;
    private String UID;


    public SignUpDB(Signup_Activity par){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        parent = par;

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        success = false;
    }


    public boolean isSuccess() {
        return success;
    }


    public void setSuccess(boolean success) {this.success = success;
    }


    public void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(parent, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            getNewUID();
                            setUser();
                            parent.nextActivity();
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


    private void getNewUID(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
        }else{
            UID = "NULL";
        }
    }

    public void setUser() {
        Log.d(TAG, "IN setUser");
        Log.d(TAG, parent.getUserEmail());

        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        //String key = mDatabase.push().getKey();

        User newUser = parent.getUser();
        newUser.setUID(UID);
        String key = UID;

        mDatabase.child(key).setValue(newUser);

    }
}