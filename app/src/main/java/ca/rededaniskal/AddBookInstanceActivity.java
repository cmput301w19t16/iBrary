package ca.rededaniskal;

/*author : Skye*/

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddBookInstanceActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference bookRef;

    private EditText title, author, isbn;
    private Button addBookButton;
    private Button scanBarcode;

   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_instance);




        addBookButton = findViewById(R.id.addBookButton);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title =  findViewById(R.id.addBookEditTitle);
                author =  findViewById(R.id.addBookEditAuthor);
                isbn =  findViewById(R.id.addBookEditISBN);
                String userID = "joe";
                String Title =  title.toString();
                String Author =  author.toString();
                String ISBN =  isbn.toString();
                BookInstance bookInstance = new BookInstance(Title, Author, ISBN, userID, userID,"Good", "a" );
                FirebaseDatabase Database = FirebaseDatabase.getInstance();
                DatabaseReference bookRef = Database.getReference().child("book-instances").child(bookInstance.getBookID());
                bookRef.setValue(bookInstance);
            }
        };

        addBookButton.setOnClickListener(onClickListener);





        }







    private void addBookInstance(){
        String userID = "joe";
        String Title =  title.toString();
        String Author =  author.toString();
        String ISBN =  isbn.toString();
        if (TextUtils.isEmpty(Title) || TextUtils.isEmpty(Author) || TextUtils.isEmpty(ISBN)){
            Toast.makeText(this, "Insufficient information", Toast.LENGTH_SHORT);
        }



        else  {

            BookInstanceDetailsDatabase database = new BookInstanceDetailsDatabase();


            BookInstance bookInstance = new BookInstance(Title, Author, ISBN, userID, userID,"Good", "a" );
            FirebaseDatabase Database = FirebaseDatabase.getInstance();
            DatabaseReference bookRef = Database.getReference().child("book-instances").child(bookInstance.getBookID());
            bookRef.setValue(bookInstance);

            //if (database.updateBookInstance(bookInstance)){
                Toast.makeText(this, "Book Saved", Toast.LENGTH_SHORT);
            //}
            //else{
              //  Toast.makeText(this, "Book Not Saved", Toast.LENGTH_SHORT);
            //}



        }


    }

}
