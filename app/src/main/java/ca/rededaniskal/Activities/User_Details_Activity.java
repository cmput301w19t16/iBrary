/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View details of another user
 *
 * ISSUES:
 * Need to be able to send requests to this user
 *
 */package ca.rededaniskal.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.rededaniskal.Database.Follow_DB;
import ca.rededaniskal.BusinessLogic.myCallbackBool;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

/**
 * @author Daniela, Revan
 */

public class User_Details_Activity extends AppCompatActivity {

    private User user_received;

    TextView DisplayUsername;
    TextView DisplayLocation;
    TextView DisplayEmail;
    TextView DisplayPhoneNum;
    TextView DisplayTotalFollowers;

    ImageView UserPic;

    private boolean swapping;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private Follow_DB fdb;
    private boolean isFollowing;

    private Button Follow_or_unfollow;
    private myCallbackBool mcb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__details_);

        Follow_or_unfollow = findViewById(R.id.Add_delete);

        Intent intent = getIntent();

        user_received = (User) intent.getSerializableExtra("user");
        fillData(user_received);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        swapping = false;

        mcb = new myCallbackBool() {
            @Override
            public void onCallback(Boolean value) {
                isFollowing = value;
                int followcountchange;
                if (isFollowing){
                    followcountchange = -1;
                }
                else{
                    followcountchange = 1;
                }
                if (swapping){
                    fdb.swapFollow(currentUser.getUid(), user_received.getUID(), isFollowing);
                    swapping = false;
                    isFollowing = !isFollowing;
                    user_received.setFollowerCount(user_received.getFollowerCount() + followcountchange);
                }
                setFriendText();
                fillData(user_received);
            }
        };

        fdb = new Follow_DB();
        fdb.isFollowing(currentUser.getUid(), user_received.getUID(), mcb);

        Follow_or_unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendButtonPressed();
            }
        });

    }

        public void fillData(User user) {

        DisplayUsername = (TextView) findViewById(R.id.DisplayUserName);
        DisplayLocation = (TextView) findViewById(R.id.DisplayLocation);
        DisplayEmail = (TextView) findViewById(R.id.DisplayEmail);
        DisplayPhoneNum = (TextView) findViewById(R.id.DisplayPhoneNumber);
        DisplayTotalFollowers = (TextView) findViewById(R.id.UserMutualFriends);

        String username = user.getUserName();
        String location = user.getLocation();
        String email = user.getEmail();
        String phone_num = user.getPhoneNumber();

        DisplayUsername.setText(username);
        DisplayLocation.setText(location);
        DisplayEmail.setText(email);
        DisplayPhoneNum.setText(phone_num);
        DisplayTotalFollowers.setText(Integer.toString(user_received.getFollowerCount()));

    }

    public void friendButtonPressed(){
        swapping = true;
        fdb.isFollowing(currentUser.getUid(), user_received.getUID(), mcb);
    }

    public void setFriendText(){
        if (isFollowing){
            Follow_or_unfollow.setText("Unfollow");
            Follow_or_unfollow.setBackgroundColor(getResources()
                    .getColor(R.color.denyRed, getTheme()));
        }
        else{
            Follow_or_unfollow.setText("Follow");
            Follow_or_unfollow.setBackgroundColor(getResources()
                    .getColor(R.color.acceptGreen, getTheme()));
        }
    }
}
