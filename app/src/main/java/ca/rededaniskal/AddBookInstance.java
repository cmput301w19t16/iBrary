package ca.rededaniskal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AddBookInstance extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference bookRef;
    String currentUserID;
    private EditText title, author, isbn;
    private  Button addBookButton;
    private Button scanBarcode;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getUid();
        bookRef = FirebaseDatabase.getInstance().getReference().child("bookInstances");

        addBookButton = (Button) findViewById(R.id.addBookButton);
        title =  findViewById(R.id.addBookEditTitle);
        author =  findViewById(R.id.addBookEditAuthor);
        isbn =  findViewById(R.id.addBookEditISBN);



        addBookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v){
                addBookInstance();
            }


        });

    }

    private void addBookInstance(){
        String userID = currentUserID;
        String Title =  title.toString();
        String Author =  author.toString();
        String ISBN =  isbn.toString();
        if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Author) || TextUtils.isEmpty(ISBN)){
            Toast.makeText(this, "Insufficient information", Toast.LENGTH_SHORT);
        }


        else  {

            BookInstance bookInstance = new BookInstance(Title, Author, ISBN, userID, userID,"Good", "Available" );
            bookRef.child(bookInstance.getBookID()).setValue(bookInstance).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(AddBookInstance.this, Book_Details_Activity.class);
                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(AddBookInstance.this, "Could not add book", Toast.LENGTH_SHORT);
                    }

                }
            });



        }


    }

}
