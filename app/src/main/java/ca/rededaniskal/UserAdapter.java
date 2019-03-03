package ca.rededaniskal;
// Created by Revan on 2019-03-03

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
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
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.user_list_view, null);
        UserViewHolder holder = new BookViewHolder(view);
        return holder;
    }

    /**
     * Binds an Entry to a view holder.
     *
     * @param bookViewHolder      the the view to be bound to
     * @param i                 position of Entry in list
     */
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder  userViewHolder, final int i) {
        final User user = users.get(i);

        //Set the user attributes
        userViewHolder.bookTitle.setText(book.getTitle());


        //if User clicks on a Book, will start the book details Activity
        userViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mctx, BookDetailsActivity.class); // TODO: change the name of this for the
                bundle.putSerializable("KEY", book);
                intent.putExtras(bundle); // Pass in the postion of entry to be changed in the list
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
            UserName = itemView.findViewById(R.id.UserName);
            UserLocation = itemView.findViewById(R.id.UserLocation);
            UserMutualFriends = itemView.findViewById(R.id.UserMutualFriends);
        }
    }
}
