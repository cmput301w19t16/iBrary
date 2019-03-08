package ca.rededaniskal;

/*author : Skye*/


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBookInstanceActivity extends AppCompatActivity {
    private static final String TAG = "AddBookInstanceActivity";


    private EditText title, author, isbn;
    private Button addBookButton;
    private Button scanBarcode;
    private FirebaseDatabase Database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //database = new BookInstanceDetailsDatabase();

        setContentView(R.layout.activity_add_book_instance);
        FirebaseDatabase Database = FirebaseDatabase.getInstance();
        title = findViewById(R.id.addBookEditTitle);
        author = findViewById(R.id.addBookEditAuthor);
        isbn = findViewById(R.id.addBookEditISBN);
        addBookButton = findViewById(R.id.addBookButton);



        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addBookInstance();


            }
        } ;
        addBookButton.setOnClickListener(onClickListener);





    }








    private void addBookInstance( ){
        
        String userID = "joe";
        String Title =  title.getText().toString();
        String Author =  author.getText().toString();
        String ISBN =  isbn.getText().toString();
        if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Author) || TextUtils.isEmpty(ISBN)){

            return;
        }
        else {





            BookInstance bookInstance = new BookInstance(Title, Author, ISBN, userID, userID, "Good", "a");

            DatabaseReference bookRef = Database.getReference().child("book-instances").child(bookInstance.getBookID());
            bookRef.setValue(bookInstance);

            //database.updateBookInstance(bookInstance);
        }






    }

}
