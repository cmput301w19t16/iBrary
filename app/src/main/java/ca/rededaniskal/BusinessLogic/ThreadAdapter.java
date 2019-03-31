package ca.rededaniskal.BusinessLogic;
//fCreated by Revan on 2019-03-22

/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing a Thread
 *
 * ISSUES:
 */


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.Comment;
import ca.rededaniskal.R;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder>{

    public static final String REPLIED = "replied";
    public Context mctx;
    private ArrayList<Comment> comments;

    public ThreadAdapter(Context mctx, ArrayList<Comment> comments) {
        this.mctx = mctx;
        this.comments = comments;
    }


    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(R.layout.thread_card, viewGroup, false);
        ThreadViewHolder holder = new ThreadViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ThreadViewHolder ThreadViewHolder, final int i) {
        final Comment comment = comments.get(i);

        //TODO: Set profile pictures
        //profilePicture = itemView.findViewById(R.id.profilePicture);
        ThreadViewHolder.text.setText(comment.getText());
        ThreadViewHolder.name.setText(comment.getCreator());
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }


    class ThreadViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        TextView text, name;

        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            text = itemView.findViewById(R.id.topic);
            name = itemView.findViewById(R.id.name);
        }
    }
}
