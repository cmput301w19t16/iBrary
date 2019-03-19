/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing Users
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;
// Created by Revan on 2019-03-03

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import ca.rededaniskal.Activities.User_Details_Activity;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private User globalUser = new User("username", "email", "location");
    public Context mctx;
    private ArrayList<User> users;

    /**
     * Instantiates a new User adapter.
     */
    public UserAdapter(Context mctx, ArrayList<User> users) {
        this.mctx = mctx;
        this.users = users;
    }

    /**
     * When View Holder is created
     *
     */
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.user_list_view, null);
        UserViewHolder holder = new UserViewHolder(view);
        return holder;
    }

    /**
     * Binds an Entry to a view holder.
     *
     * @param i                 position of Entry in list
     */
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder  userViewHolder, final int i) {
        final User user = users.get(i);

        //Set the user attributes
        userViewHolder.UserName.setText(user.getUserName());
        userViewHolder.UserLocation.setText(user.getLocation());
        userViewHolder.UserMutualFriends.setText(globalUser.numberMutualFriends(user).toString());// TODO: Implement global User
        //TODO: Set Profile Pic

        //If they are Friends, set the icon to the friends icon
        if ( true ){
            userViewHolder.statusIcon.setImageResource(R.drawable.ic_person_black_24dp);
        }else{
            userViewHolder.statusIcon.setImageResource(R.drawable.ic_person_add_black_24dp);
        }

        //Set on click listener for the icon (in order to add friends)
        userViewHolder.statusIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!globalUser.isFriendsWith(user)){ // if they are not friends
                    //TODO: DB add request to db
                }
            }
        });

        //if User clicks on another User, will start the user details Activity
        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, User_Details_Activity.class); // TODO: change the name of this for the
                intent.putExtra("user", user);
                mctx.startActivity(intent);
            }
        });
    }

    /**
     * returns the size of the Entry list
     *
     * @return  EntryList.size()
     */
    @Override
    public int getItemCount() {
        return users.size();
    }

    /**
     * The type Entry view holder, the obbject to actually hold an entry
     */
    class UserViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic, statusIcon;
        TextView UserName, UserLocation, UserMutualFriends;

        /**
         * Instantiates a new Entry view holder.
         */
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ProfilePicture); //TODO: Make this display the Users image
            statusIcon = itemView.findViewById(R.id.StatusIcon);
            UserName = itemView.findViewById(R.id.requestInfo);
            UserLocation = itemView.findViewById(R.id.BookAuthor);
            UserMutualFriends = itemView.findViewById(R.id.UserMutualFriends);
        }
    }
}
