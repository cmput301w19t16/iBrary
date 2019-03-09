package ca.rededaniskal.BusinessLogic;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.Activities.Fragments.Post_Feed_Fragment;
import ca.rededaniskal.Activities.Fragments.View_Own_Profile_Fragment;
import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.View_Rating_Post_Activity;
import ca.rededaniskal.Activities.View_Text_Post_Activity;
import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> mDataset;
    public Post_Feed_Fragment fragment;

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public String postType;
        public String postID;
        public View view;

        public PostViewHolder(View v){
            super(v);
            view = v;
            textView = v.findViewById(R.id.post_text_view);
        }
    }

    public PostAdapter(ArrayList<Post> postList, Post_Feed_Fragment frag){
        this.fragment = frag;
        this.mDataset = postList;
    }
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.post_card_layout, parent, false);

        PostViewHolder vh = new PostViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position){
        Post post = mDataset.get(position);
        String text = post.getUserName();
        switch (post.getType()){
            case "Rating_Post":
                text += " just reviewed " + post.getISBN();

                break;
            case "Text_Post":
                text += " made a post about " + post.getISBN();
                break;
            case "alert":
                text = post.getMessage();
        }

        holder.textView.setText(text);
        holder.postType = post.getType();
        holder.postID = post.getID();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (holder.postType){
                    case "Text_Post":
                        intent = new Intent(fragment.getContext(), View_Text_Post_Activity.class);
                        break;
                    case "Rating_Post":
                        intent = new Intent(fragment.getContext(), View_Rating_Post_Activity.class);
                        break;
                    default:
                        intent = new Intent(fragment.getContext(), Login_Activity.class);
                        break;

            }
            fragment.startActivity(intent);
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
