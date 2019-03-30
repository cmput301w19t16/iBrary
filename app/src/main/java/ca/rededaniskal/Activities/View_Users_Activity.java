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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.BusinessLogic.myCallbackUidList;
import ca.rededaniskal.BusinessLogic.myCallbackUserList;
import ca.rededaniskal.Database.Follow_DB;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.EntityClasses.Book_List;
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
    private myCallbackUidList mcbuid;


    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__friends_);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fdb = new Follow_DB();
        udb = new Users_DB();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Friends = new ArrayList<>();

        mode = getIntent().getStringExtra("mode");

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

        mcbuid = new myCallbackUidList() {
            @Override
            public void onCallback(ArrayList<String> uidList) {
                Users_DB udb = new Users_DB();
                udb.getListOfUsers(uidList, mcbul);
            }
        };


        if (mode.equals("following")){
            getSupportActionBar().setTitle("Users you Follow");
            fdb.getLeaders(user.getUid(), mcbuid);
        }else{
            getSupportActionBar().setTitle("Your Followers");
            fdb.getFollowers(user.getUid(), mcbuid);
        }






    }

    private void updateRecyclerView(){
        recyclerView = (RecyclerView) findViewById(R.id.DisplayFriends);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this, Friends);
        recyclerView.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }
}
