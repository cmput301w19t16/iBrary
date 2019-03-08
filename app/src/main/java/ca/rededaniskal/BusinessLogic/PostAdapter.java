package ca.rededaniskal.BusinessLogic;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> mDataset;

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public String postType;
        public String postID;
        public PostViewHolder(View v){
            super(v);
            textView = v.findViewById(R.id.post_text_view);
        }
    }

    public PostAdapter(ArrayList<Post> postList){
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
    public void onBindViewHolder(PostViewHolder holder, int position){
        Post post = mDataset.get(position);
        String text = post.getUserName();
        switch (post.getType()){
            case "ratingPost":
                text += " just reviewed " + post.getISBN();

                break;
            case "textPost":
                text += " made a post about " + post.getISBN();
                break;
        }
        holder.textView.setText(text);
        holder.postType = post.getType();
        holder.postID = post.getID();
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }


}
