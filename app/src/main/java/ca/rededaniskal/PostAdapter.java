package ca.rededaniskal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private ArrayList<Post> mDataset;

    public class PostViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public PostViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }

    public PostAdapter(){
    }
    @Override
    public PostAdapter.PostViewHolder onCreateViewHolder (ViewGroup parent, int viewType){
        TextView v = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_text_view,
                parent, false);

        PostViewHolder vh = new PostViewHolder(v);
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
    }

    @Override
    public int getItemCount(){
        return mDataset.size();
    }


}
