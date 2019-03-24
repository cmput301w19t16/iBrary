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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.rededaniskal.EntityClasses.Master_Book;
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
    TextView DisplayMutualFriends;

    TextView DisplayFavTitle;
    TextView DisplayFavAuthor;
    TextView DisplayFavISBN;

    ImageView BookCover;
    ImageView UserPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__details_);

        Intent intent = getIntent();

        user_received = (User) intent.getSerializableExtra("user");
        fillData(user_received);

    }

        public void fillData(User user) {

        DisplayUsername = (TextView) findViewById(R.id.DisplayUserName);
        DisplayLocation = (TextView) findViewById(R.id.DisplayLocation);
        DisplayEmail = (TextView) findViewById(R.id.DisplayEmail);
        DisplayPhoneNum = (TextView) findViewById(R.id.DisplayPhoneNumber);
        DisplayMutualFriends = (TextView) findViewById(R.id.UserMutualFriends);

        DisplayFavTitle = (TextView) findViewById(R.id.DisplayBookTitle);
        DisplayFavAuthor = (TextView) findViewById(R.id.DisplayBookAuthor);
        DisplayFavISBN = (TextView) findViewById(R.id.DisplayBookISBN);

        UserPic = (ImageView) findViewById(R.id.ProfilePicture);
        BookCover = (ImageView) findViewById(R.id.DisplayFavBookCover);

        String username = user.getUserName();
        String location = user.getLocation();
        String email = user.getEmail();
        String phone_num = user.getPhoneNumber();
//        Integer mutual_friends = user.numberMutualFriends(user);
        Integer mutual_friends = 0;


        DisplayUsername.setText(username);
        DisplayLocation.setText(location);
        DisplayEmail.setText(email);
        DisplayPhoneNum.setText(phone_num);
        DisplayMutualFriends.setText(mutual_friends.toString().concat(" Mutual Friends"));

    }


    //Enclosed BD helper class
    public class userDetailsDB{
        private FirebaseAuth mAuth;
        private String email;
        private String username;
        private String location;
        private String phone;

        public userDetailsDB() {
        }
    }
}
