package ca.rededaniskal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddBookInstance extends AppCompatActivity {

    private String title, author, ISBN;
    private  Button addBookButton;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);
        addBookButton = (Button) findViewById(R.id.addBookButton);


        addBookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                addBookInstance();
            }


        });

    }

    private void addBookInstance(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null){
            //goto login activity


        }
        else {
            String userID = user.getUid();

            title =  findViewById(R.id.addBookEditTitle).toString();
            author =  findViewById(R.id.addBookEditAuthor).toString();
            ISBN =  findViewById(R.id.addBookEditISBN).toString();

            BookInstance bookInstance = new BookInstance(title, author, ISBN, userID, userID,"Good", "Available" );

            DatabaseReference myRef= FirebaseDatabase.getInstance().getReference();
            DatabaseReference bookInstanceRef = myRef.child("bookInstances");
            bookInstanceRef.push().setValue(bookInstance);
            DatabaseReference masterBookRef = myRef.child("masterBook");
            //masterBookRef.addListenerForSingleValueEvent(new ValueEventListener(){
             //   @Override
               // void onDataChange(DataSnapshot dataSnapshot){
                 //   if (!(dataSnapshot.hasChild(ISBN))){
                   //     MasterBook masterBook = new MasterBook(title, author,ISBN);

                    //}
                //}
           // } );







        }


    }

}
