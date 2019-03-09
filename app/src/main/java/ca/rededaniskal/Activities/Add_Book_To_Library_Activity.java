package ca.rededaniskal.Activities;



//author : Skye

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import ca.rededaniskal.BusinessLogic.AddBookLogic;
import ca.rededaniskal.Database.Data_Provider;
import ca.rededaniskal.EntityClasses.Book_Instance;


import ca.rededaniskal.R;

public class Add_Book_To_Library_Activity extends AppCompatActivity {
    private static final String TAG = "Add_Book_To_Library_Activity";

    private EditText title, author, isbn;
    private Button addBookButton;

    private AddBookLogic businessLogic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_add_book_instance);
        addBookButton = findViewById(R.id.addBookButton);


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
                validateFields();
                addBookInstance();


            }
        };
        addBookButton.setOnClickListener(onClickListener);


    }


    public void getInfo() {
        title = findViewById(R.id.addBookEditTitle);
        author = findViewById(R.id.addBookEditAuthor);
        isbn = findViewById(R.id.addBookEditISBN);


        String Title = title.getText().toString();
        String Author = author.getText().toString();
        String ISBN = isbn.getText().toString();

        businessLogic = new AddBookLogic(Title, Author, ISBN);

    }

    public void validateFields() {
        String error = businessLogic.validateAuthor();
        if (!error.equals("")){
            author.setError(error);

        }
        String error1 = businessLogic.validateAuthor();
        if (!error1.equals("")){
            author.setError(error1);

        }
        String error2 = businessLogic.validateAuthor();
        if (!error2.equals("")){
            author.setError(error2);

        }

    }


    public void addBookInstance() {
        if (businessLogic.isValid()) {


            String userID = "del@del.com";
            String Title = title.getText().toString();
            String Author = author.getText().toString();
            String ISBN = isbn.getText().toString();


            Book_Instance bookInstance = new Book_Instance(Title, Author, ISBN, userID, userID, "Good", "a");

            Data_Provider data_provider = new Data_Provider();

            if (data_provider.insert(bookInstance)){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            }






        }

    }
}


