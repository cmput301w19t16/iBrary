package ca.rededaniskal.Activities;

/*author : Skye*/

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.AddBookLogic;
import ca.rededaniskal.EntityClasses.Book_Instance;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.Database.BookInstanceDetailsDatabase;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.R;

public class Add_Book_To_Library_Activity extends AppCompatActivity {
    private static final String TAG = "Add_Book_To_Library_Activity";
    private FirebaseAuth mAuth;
    private DatabaseReference bookRef;

    //UI stuff

    private EditText addTitle, addAuthor, addISBN, addDescription;
    private Button openScanner, addBook;
    private FloatingActionButton openCamera;

    private AddBookLogic businessLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);
        
        //Set buttons and EditTexts
        addTitle = findViewById(R.id.addTitle);
        addAuthor = findViewById(R.id.addAuthor);
        addISBN = findViewById(R.id.addISBN);

        openScanner = findViewById(R.id.openScanner);
        openCamera = findViewById(R.id.openCamera);
        addBook = findViewById(R.id.addBook);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
                validateFields();
                addBookInstance();


            }
        };
        addBook.setOnClickListener(onClickListener);


    }


    public void getInfo() {
        //title = findViewById(R.id.addBookEditTitle);
        //author = findViewById(R.id.addBookEditAuthor);
        //isbn = findViewById(R.id.addBookEditISBN);


        String Title = addTitle.getText().toString();
        String Author = addAuthor.getText().toString();
        String ISBN = addISBN.getText().toString();

        businessLogic = new AddBookLogic(Title, Author, ISBN);

    }

    public void validateFields() {
        String error = businessLogic.validateAuthor();
        if (!error.equals("")){
            addAuthor.setError(error);

        }
        String error1 = businessLogic.validateAuthor();
        if (!error1.equals("")){
            addAuthor.setError(error1);

        }
        String error2 = businessLogic.validateAuthor();
        if (!error2.equals("")){
            addAuthor.setError(error2);

        }

    }


    public void addBookInstance() {
        if (businessLogic.isValid()) {


            String userID = "del@del.com";
            String Title = addTitle.getText().toString();
            String Author = addAuthor.getText().toString();
            String ISBN = addISBN.getText().toString();


            Book_Instance bookInstance = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "a");


            if( !businessLogic.addBookSuccess( bookInstance ).equals("")){
                Toast.makeText(this, "Book Saved!", Toast.LENGTH_SHORT);

            }
            else{
                Toast.makeText(this, "Database Error!", Toast.LENGTH_SHORT);
            }



        }

    }
}


