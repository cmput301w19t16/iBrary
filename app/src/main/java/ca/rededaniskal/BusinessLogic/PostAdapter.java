/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing Posts
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Fragments.Post_Feed_Fragment;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Users_DB;

import ca.rededaniskal.EntityClasses.Display_Post;

import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

/**
 * This is an adapter that gets the values of the posts from the database
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    public Post_Feed_Fragment mctx;
    private ArrayList<Display_Post> posts;

    /**
     * Instantiates a new Entry adapter.
     */
    public PostAdapter(Post_Feed_Fragment ctx, ArrayList<Display_Post> posts) {
        this.mctx = ctx;
        this.posts = posts;
    }

    /**
     * When View Holder is created
     *
     */
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.post_card_layout, viewGroup, false);
        PostViewHolder holder = new PostViewHolder(view);
        return holder;
    }

    /**
     * Binds an Entry to a view holder.
     *
     * @param postViewHolder      the the view to be bound to
     * @param i                 position of Entry in list
     */
    @Override
    public void onBindViewHolder(@NonNull final PostViewHolder postViewHolder, final int i) {
        final Display_Post display = posts.get(i);
        final Post post = display.getPost();

        //Set the book attributes
        postViewHolder.user.setText(display.getPoster());
        postViewHolder.title.setText(display.getTitle());
        postViewHolder.topic.setText(post.getTopic());
        postViewHolder.text.setText(post.getText());

        Users_DB usersDb = new Users_DB();

        myCallbackUser myCallbackUser = new myCallbackUser() {
            @Override
            public void onCallback(User user) {
                String urlProfilePic = user.getProfilePic();
                if(urlProfilePic != null){
                    Log.d("USER PROFILE PICTURE", "WE ARE HERE");
                    LoadImage loader = new LoadImage(postViewHolder.userPic);
                    loader.execute(urlProfilePic);
                }
            }
        };

        String uid = post.getUid();
        Log.d("UID", uid);
        usersDb.getUser(uid, myCallbackUser);
    }

    /**
     * returns the size of the Entry list
     *
     * @return  EntryList.size()
     */
    @Override
    public int getItemCount() {
        if (posts == null){
            return 0;
        }
        return posts.size();
    }

    /**
     * The type Entry view holder, the object to actually hold an entry
     */
    class PostViewHolder extends RecyclerView.ViewHolder {

        ImageView userPic;
        TextView user, title, topic, text;
        /**
         * Instantiates a new Entry view holder.
         */
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            user = itemView.findViewById(R.id.user);
            title = itemView.findViewById(R.id.Title);
            topic = itemView.findViewById(R.id.topic);
            text = itemView.findViewById(R.id.text);
            userPic = itemView.findViewById(R.id.profilePic);
        }
    }
}
