package ca.rededaniskal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

public class View_Book_Request_Activity extends AppCompatActivity {

    private TextView nameField;
    private TextView locationField;
    private TextView followersField;
    private CardView cardView;
    private User user;
    private Users_DB udb;
    private String uid;
    private BorrowRequest br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__book__request_);

        br = (BorrowRequest) getIntent().getSerializableExtra("request");
        uid = br.getsenderUID();

        udb = new Users_DB();
        user = udb.getUser(uid);

        nameField = findViewById(R.id.username);
        locationField = findViewById(R.id.location);
        followersField = findViewById(R.id.followers);
        cardView = findViewById(R.id.cardname);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), User_Details_Activity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        nameField.setText(user.getUserName());
        locationField.setText(user.getLocation());
        followersField.setText("0");

    }

}
