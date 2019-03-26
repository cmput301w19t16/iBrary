/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing Posts
 *
 * ISSUES:
 */
package ca.refactored.BusinessLogic;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.refactored.Activities.Fragments.Post_Feed_Fragment;
import ca.refactored.Activities.View_Rating_Post_Activity;
import ca.refactored.Activities.View_Text_Post_Activity;
import ca.refactored.EntityClasses.Post;
import ca.refactored.R;

//Author: Nick
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> mDataset;
    public Post_Feed_Fragment fragment;

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView postTitle;
        public TextView postBodyText;
        public String postType;
        public String postID;
        public View view;

        public PostViewHolder(View v){
            super(v);
            view = v;
            postTitle = v.findViewById(R.id.post_title);
            postBodyText = v.findViewById(R.id.post_body_text);
        }
    }


    public PostAdapter(ArrayList<Post> postList, Post_Feed_Fragment frag){
        this.fragment = frag;
        this.mDataset = postList;
    }


    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        //Set the Layout
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.post_card_layout, parent, false);

        PostViewHolder vh = new PostViewHolder(itemView);
        return vh;
    }


    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position){
        //Bind a post to the view
        Post post = mDataset.get(position);
        String titleText = post.getUserName();
        String bodyText = "";

        //Set the body text based on post type
        switch (post.getType()){
            case "Rating_Post":
                titleText += " just reviewed " + post.getISBN();
                bodyText += post.getMessage();
                break;
            case "Text_Post":
                titleText += " made a post about " + post.getISBN();
                bodyText += post.getMessage();
                break;
            case "alert":
                titleText = post.getMessage();
        }

        holder.postTitle.setText(titleText);
        holder.postBodyText.setText(bodyText);
        holder.postType = post.getType();
        holder.postID = post.getID();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (holder.postType){
                    case "Text_Post":
                        intent = new Intent(fragment.getContext(), View_Text_Post_Activity.class);
                        fragment.startActivity(intent);
                        break;
                    case "Rating_Post":
                        intent = new Intent(fragment.getContext(), View_Rating_Post_Activity.class);
                        fragment.startActivity(intent);
                        break;
                    default:
                        Toast.makeText(fragment.getActivity(), "All out of posts. " +
                                "Refresh for more!", Toast.LENGTH_LONG).show();
                        break;
            }
        }
    });

    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }


    public void clear() {
        mDataset.clear();
        this.checkEmpty();
        this.notifyDataSetChanged();
    }


    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        mDataset.addAll(list);
        this.notifyDataSetChanged();
    }


    public void checkEmpty(){
        if (mDataset.size() == 0){
            mDataset.add(new Post("Looks like there are no more posts",
                    "system", "None", "Display"));
        }
        return;
    }
}
