/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View all Users
 * Temporary until search is implemented
 *
 * ISSUES:
 *
 */
package ca.refactored.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ca.refactored.BusinessLogic.UserAdapter;
import ca.refactored.Database.GetAllUsersDB;
import ca.refactored.EntityClasses.User;
import ca.refactored.R;

//Author: Revan
public class View_All_Users_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private ArrayList<User> Users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__users_);

        Users = new ArrayList<>(); //TODO: DB get friends of the current user

        //Set the recycler view
        recyclerView = findViewById(R.id.DisplayUsers);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this, Users);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
        GetAllUsersDB db = new GetAllUsersDB(this);
    }

    public void Usersclear(){Users.clear(); return;}

    public void Usersadd(User u){Users.add(u);return;}

    public void notifyData(){userAdapter.notifyDataSetChanged();return;}
}



