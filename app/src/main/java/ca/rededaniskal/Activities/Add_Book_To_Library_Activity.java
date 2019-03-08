package ca.rededaniskal.Activities;

/*author : Skye*/

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.rededaniskal.Database.BookInstanceDetailsDatabase;
import ca.rededaniskal.EntityClasses.BookInstance;
import ca.rededaniskal.R;

public class Add_Book_To_Library_Activity extends AppCompatActivity {
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

            BookInstanceDetailsDatabase database = new BookInstanceDetailsDatabase();


            BookInstance bookInstance = new BookInstance(Title, Author, ISBN, userID, userID,"Good", "a" );
            database.updateBookInstance(bookInstance);



        }


    }

}
