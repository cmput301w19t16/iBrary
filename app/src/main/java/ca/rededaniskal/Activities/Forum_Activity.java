package ca.rededaniskal.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.ThreadAdapter;
import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.Database.GetAllUsersDB;
import ca.rededaniskal.EntityClasses.Forum;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

public class Forum_Activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ThreadAdapter threadAdapter;
    private Forum forum;
    private TextView title;
    private FloatingActionButton addTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_);

        //TODO: get Forum from DB or something
        Master_Book b = new Master_Book("Happy Potter","JK Rowling","1234567890");

        forum = new Forum(b); //For testing

        title = findViewById(R.id.Title);
        addTopic = findViewById(R.id.addTopic);

        //Set the recycler view
        recyclerView = findViewById(R.id.ViewThreads);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        threadAdapter = new ThreadAdapter(this, forum.getThreads());

        recyclerView.setAdapter(threadAdapter);
        threadAdapter.notifyDataSetChanged();
        //GetAllUsersDB db = new GetAllUsersDB(this); TODO something with this

        title.setText(forum.getBookName());

        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: adding forums
            }
        });
    }
}
