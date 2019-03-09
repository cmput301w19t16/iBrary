package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.R;



public class View_My_Library_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //For testing
        Book_List BL = new Book_List();
        Book_Instance HP = new Book_Instance("HappyPotter", "JK", "123", "Revan", "Daniela", "Very Nice", "Requested");
        BL.addBook(HP);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__my__library_);

        recyclerView = (RecyclerView) findViewById(R.id.DisplayBooks);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookAdapter = new BookAdapter(this, BL);
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        fab = (FloatingActionButton) findViewById(R.id.addBookToLibrary);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Add_Book_To_Library_Activity.class);
                startActivity(intent);


            }
        });

    }
}
