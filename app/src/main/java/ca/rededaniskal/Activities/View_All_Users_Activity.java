package ca.rededaniskal.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

//Author: Revan
public class View_All_Users_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__users_);

        ArrayList<User> Users = new ArrayList<>(); //TODO: DB get friends of the current user

        recyclerView =  findViewById(R.id.DisplayUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this, Users);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }
}
