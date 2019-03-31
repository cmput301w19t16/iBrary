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
package ca.rededaniskal.Activities;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.BusinessLogic.myCallbackStringList;
import ca.rededaniskal.BusinessLogic.myCallbackUserList;
import ca.rededaniskal.Database.Follow_DB;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

//Author: Revan
public class View_Users_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private String mode;

    private Follow_DB fdb;
    private Users_DB udb;
    private myCallbackUserList mcbul;
    private ArrayList<User> Friends;
    private myCallbackStringList mcbuid;
    SwipeRefreshLayout swipeContainer;

    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the views
        setContentView(R.layout.activity_view__users_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        swipeContainer = findViewById(R.id.swipeContainer);
        recyclerView =  findViewById(R.id.DisplayFriends);

        //Init some DB stuff
        fdb = new Follow_DB();
        udb = new Users_DB();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Friends = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Set the toolbar title
        setSupportActionBar(toolbar);
        mode = getIntent().getStringExtra("mode");
        if (mode.equals("following")){
            getSupportActionBar().setTitle("Users you Follow");
        }else{
            getSupportActionBar().setTitle("Your Followers");
        }

        mcbul = new myCallbackUserList() {
            @Override
            public void onCallback(ArrayList<User> userArrayList) {
                Friends = userArrayList;
                if (Friends.isEmpty()){
                    Log.d("Friends is empty! ", "onCallback: ");
                }
                else {
                    updateRecyclerView();
                }
            }
        };

        mcbuid = new myCallbackStringList() {
            @Override
            public void onCallback(ArrayList<String> strList) {
                Users_DB udb = new Users_DB();
                udb.getListOfUsers(strList, mcbul);
            }
        };


        // Inflate the layout for this fragment
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateFollowers();
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)

                        swipeContainer.setRefreshing(false);
                    }
                }, 300); // Delay in millis

            }
        });

        updateFollowers();

    }

    private void updateRecyclerView(){
        userAdapter = new UserAdapter(this, Friends);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }

    private void updateFollowers(){
        if (mode.equals("following")){
            fdb.getLeaders(user.getUid(), mcbuid);
        }else{
            fdb.getFollowers(user.getUid(), mcbuid);
        }
    }
}
