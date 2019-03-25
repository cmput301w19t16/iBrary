package ca.rededaniskal.Database;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AuthentificationDb extends Entity_Database {

    public AuthentificationDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return null;
    }



    public FirebaseAuth getAuthDb(){
        return mauth;

    }

    public FirebaseUser getUser(){

        return mauth.getCurrentUser();
    }


}
