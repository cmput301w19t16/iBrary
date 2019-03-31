package ca.rededaniskal.BusinessLogic;
//Created by Revan on 2019-03-24

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

import ca.rededaniskal.Activities.View_Thread_Activity;
import ca.rededaniskal.EntityClasses.Thread;
import ca.rededaniskal.R;

public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumViewHolder>{

    public static final String REPLIED = "replied";
    public Context mctx;
    private ArrayList<Thread> threads;

    public ForumAdapter(Context mctx, ArrayList<Thread> threads) {
        this.mctx = mctx;
        this.threads = threads;
    }


    @NonNull
    @Override
    public ForumViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(R.layout.forum_card, viewGroup, false);
        ForumViewHolder holder = new ForumViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ForumViewHolder forumViewHolder, final int i) {
        final Thread child_thread = threads.get(i);


        //TODO: Set profile pictures
        //profilePicture = itemView.findViewById(R.id.profilePicture);

        forumViewHolder.text.setText(child_thread.getText());
        forumViewHolder.name.setText(child_thread.getCreator());
        forumViewHolder.topic.setText(child_thread.getTopic());
        Integer numreplies = child_thread.getComments().size();

        forumViewHolder.replies.setText(Integer.toString(numreplies).concat(" replies"));

        //Set the onClickListeners
        forumViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, View_Thread_Activity.class); // TODO: change the name of this for the

                intent.putExtra("thread", child_thread);
                mctx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return threads.size();
    }


    class ForumViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        TextView text, name, topic, replies;

        public ForumViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            topic = itemView.findViewById(R.id.topic);
            name = itemView.findViewById(R.id.name);
            text = itemView.findViewById(R.id.text);
            replies = itemView.findViewById(R.id.replies);

        }
    }
}
