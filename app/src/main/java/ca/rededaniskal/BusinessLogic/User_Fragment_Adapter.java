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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Activities.User_Details_Activity;
import ca.rededaniskal.Database.Follow_DB;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

/**
 * This is an adapter that is used to get values related to followers and has logic related to
 * following or unfollowing a user from the recycler view of followers
 *
 * @since 2019-03-03
 * @author Revan
 */

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class User_Fragment_Adapter extends RecyclerView.Adapter<User_Fragment_Adapter.UserViewHolder>{
    public Context mctx;
    private ArrayList<User> users;
    public Search_Fragment fragment;


    /**
     * Instantiates a new User adapter.
     */
    public User_Fragment_Adapter(Search_Fragment ctx, ArrayList<User> users) {
        this.fragment = ctx;
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


//        View view = LayoutInflater.from(viewGroup.getContext()).
//                inflate(R.layout.master_book_card, viewGroup, false);
//
//        Master_BookViewHolder holder = new Master_BookViewHolder(view);

        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.user_list_view, viewGroup, false);
//        View view = inflater.inflate(R.layout.user_list_view, viewGroup, false);
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
        User user = users.get(i);
        Log.d("onBindViewHolder", "setUserCalled");
        userViewHolder.setUser(user);

        //Set the user attributes
        userViewHolder.UserName.setText(user.getUserName());
        userViewHolder.UserLocation.setText(user.getLocation());
        userViewHolder.UserMutualFriends.setText(Integer.toString(user.getFollowerCount()));// TODO: Implement global User
        //userViewHolder.setUser(user);

        //TODO: Set Profile Pic

        //if User clicks on another User, will start the user details Activity

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
        Button Follow_or_unfollow;
        private boolean isFollowing;
        private boolean swapping;
        private Follow_DB fdb;
        private FirebaseUser currentUser;

        private myCallbackBool mcb;

        private User user;

        /**
         * Instantiates a new Entry view holder.
         */
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("UserViewHolder", "Created User View Holder");
            mctx = fragment.getActivity().getApplicationContext();
            profilePic = itemView.findViewById(R.id.pic); //TODO: Make this display the Users image
            Follow_or_unfollow = itemView.findViewById(R.id.fufButton);
            statusIcon = itemView.findViewById(R.id.StatusIcon);
            UserName = itemView.findViewById(R.id.title);
            UserLocation = itemView.findViewById(R.id.author);
            UserMutualFriends = itemView.findViewById(R.id.UserMutualFriends);

            currentUser = FirebaseAuth.getInstance().getCurrentUser();


            /*

            I THINK THE PROBLEM HERE IS THAT ITS NOT HAVING ENOUGH TIME TO GET THE CURRENTUSER
            TRY PASSING THE CURRENT USER TO THIS ACTIVITY AS AN ARGUMENT OR SOMETHING.

             */


            swapping = false;

            fdb = new Follow_DB();




            Follow_or_unfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    friendButtonPressed();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mctx, User_Details_Activity.class); // TODO: change the name of this for the
                    intent.putExtra("user", user);
                    fragment.getActivity().startActivity(intent);
                }
            });
        }

        public void friendButtonPressed(){
            swapping = true;
            fdb.isFollowing(currentUser.getUid(), user.getUID(), mcb);
        }

        /**
         * Changes the text of the button depending on the status of the user in question
         */
        public void setFriendText(){
            if (isFollowing) {
                Log.d("", "");
                Follow_or_unfollow.setText("Unfollow");
                Follow_or_unfollow.setBackgroundColor(mctx.getResources()
                        .getColor(R.color.denyRed, mctx.getTheme()));
            }
            else{
                Log.d("", "");
                Follow_or_unfollow.setText("Follow");
                Follow_or_unfollow.setBackgroundColor(mctx.getResources()
                        .getColor(R.color.acceptGreen, mctx.getTheme()));
            }


        }

        /**
         * Changes whether or not a user is followed or unfollowed and updates the
         * number of followers that user has
         *
         * @param u     a user that is currently being followed or unfollowed
         */
        public void setUser(User u){
            user = u;
            Log.d("User_Frag_Ad", "mcb");

            mcb = new myCallbackBool() {
                @Override
                public void onCallback(Boolean value) {
                    isFollowing = value;
                    int followcountchange;
                    if (isFollowing) {
                        followcountchange = -1;
                    } else {
                        followcountchange = 1;
                    }
                    if (swapping) {
                        fdb.swapFollow(currentUser.getUid(), user.getUID(), isFollowing);
                        swapping = false;
                        isFollowing = !isFollowing;
                        user.setFollowerCount(user.getFollowerCount() + followcountchange);
                    }
                    setFriendText();
                    setFollowers();
                }
            };
            fdb.isFollowing(currentUser.getUid(), user.getUID(), mcb);
        }

        /**
         * Sets number of followers a user has
         */
        private void setFollowers(){
            UserMutualFriends.setText("Followers:   " + Integer.toString(user.getFollowerCount()));
        }




    }
}
