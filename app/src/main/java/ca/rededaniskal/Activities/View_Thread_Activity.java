package ca.rededaniskal.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import ca.rededaniskal.BusinessLogic.ThreadAdapter;
import ca.rededaniskal.EntityClasses.Forum;
import ca.rededaniskal.R;

public class View_Thread_Activity extends AppCompatActivity {

    Thread thread;
    private RecyclerView viewParent;
    private RecyclerView viewChildren;

    private ThreadAdapter threadAdapterParent;
    private ThreadAdapter threadAdapterChildren;

    private FloatingActionButton addTopic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_);

    }
}
