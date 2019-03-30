package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.ForumAdapter;
import ca.rededaniskal.BusinessLogic.ThreadAdapter;
import ca.rededaniskal.EntityClasses.Comment;
import ca.rededaniskal.EntityClasses.Thread;
import ca.rededaniskal.R;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class View_Thread_Activity extends AppCompatActivity {

    private ArrayList<Thread> displayParent;
    private Thread parent;
    private ArrayList<Comment> children;

    private RecyclerView viewChildren;
    private ThreadAdapter adapterChildren;
    private FloatingActionButton addTopic;

    private TextView name, topic, text, replies;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        parent = (Thread)  intent.getSerializableExtra("thread");
        children = parent.getComments();



        addTopic = findViewById(R.id.add);

        //Set the views for the Parent
        name = findViewById(R.id.name);
        topic = findViewById(R.id.topic);
        text = findViewById(R.id.text);
        replies = findViewById(R.id.replies);

        name.setText(parent.getCreator());
        topic.setText(parent.getTopic());
        text.setText(parent.getText());
        replies.setText(Integer.toString(parent.numComments()).concat(" replies")  );


        //Set the recycler view for the Children
        viewChildren = findViewById(R.id.ViewThreads);
        viewChildren.setHasFixedSize(true);
        viewChildren.setLayoutManager(new LinearLayoutManager(this));
        adapterChildren = new ThreadAdapter(this, children);
        viewChildren.setAdapter(adapterChildren);
        adapterChildren.notifyDataSetChanged();

        //Set the add on click listener
        addTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.add_post, null);

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true; // lets taps outside the popup also dismiss it
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                // show the popup window
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                final Button post = popupView.findViewById(R.id.post);
                final EditText text = popupView.findViewById(R.id.text);


                post.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean valid = TRUE;

                        if (text.getText().toString().trim().equalsIgnoreCase("")){
                            text.setError("Please add text to your pose");
                            valid = FALSE;
                        }

                        if (valid.equals(TRUE)) {
                            //add to Forum

                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            String textStr = text.getText().toString();

                            Comment newComment = new Comment(uid, textStr);

                            parent.addComment(newComment);

                            adapterChildren.notifyDataSetChanged();
                            popupWindow.dismiss();
                        }
                    }
                });
            }
        });
    }
}
