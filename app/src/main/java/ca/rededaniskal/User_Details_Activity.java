package ca.rededaniskal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Daniela
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
        Bundle extras = intent.getExtras();
        if (extras != null){
            user_received = (User) getIntent().getSerializableExtra("USER");
            fillData(user_received);
        }
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
        Integer mutual_friends = user.numberMutualFriends(user);

        MasterBook fav_book = user.getFavBook();
        String fav_title = fav_book.getTitle();
        String fav_author = fav_book.getAuthor();
        String fav_ISBN = fav_book.getISBN();
        //  fav_image = fav_book.get
        // user_image = ...

        DisplayUsername.setText(username);
        DisplayLocation.setText(location);
        DisplayEmail.setText(email);
        DisplayPhoneNum.setText(phone_num);
        DisplayMutualFriends.setText(mutual_friends);

        DisplayFavTitle.setText(fav_title);
        DisplayFavAuthor.setText(fav_author);
        DisplayFavISBN.setText(fav_ISBN);
    }
}