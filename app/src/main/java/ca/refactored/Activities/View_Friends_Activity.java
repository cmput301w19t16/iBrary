/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View your all your friends
 *
 * ISSUES:
 * Needs DB support
 *
 */
package ca.refactored.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import ca.refactored.BusinessLogic.UserAdapter;
import ca.refactored.EntityClasses.User;
import ca.refactored.R;

//Author: Revan
public class View_Friends_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__friends_);

        ArrayList<User> Friends = new ArrayList<>(); //TODO: DB get friends of the current user

        User user = new User("Revan", "revan@ualberta", "Red Deer");
        Friends.add(user);

        recyclerView = (RecyclerView) findViewById(R.id.DisplayFriends);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(this, Friends);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }
}
