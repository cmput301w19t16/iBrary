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
import ca.rededaniskal.Activities.View_Thread_Activity;
import ca.rededaniskal.EntityClasses.Thread;
import ca.rededaniskal.R;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class ThreadAdapter extends RecyclerView.Adapter<ThreadAdapter.ThreadViewHolder>{

    public static final String REPLIED = "replied";
    public Context mctx;
    private ArrayList<Thread> threads;

    public ThreadAdapter(Context mctx, ArrayList<Thread> threads) {
        this.mctx = mctx;
        this.threads = threads;
    }


    @NonNull
    @Override
    public ThreadViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(R.layout.thread_card, null);
        ThreadViewHolder holder = new ThreadViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ThreadViewHolder ThreadViewHolder, final int i) {
        final Thread child_thread = threads.get(i);

        //TODO: Set profile pictures
        //profilePicture = itemView.findViewById(R.id.profilePicture);


        ThreadViewHolder.text.setText(child_thread.getText());
        ThreadViewHolder.name.setText(child_thread.getCreator());

        //If this post has children,
        if (child_thread.getThreads().size() > 0){
            Thread reply = child_thread.getThreads().get(0);
            //TODO: set profile picture
            //profilePicture2 = itemView.findViewById(R.id.profilePicture2);
            ThreadViewHolder.name2.setText(reply.getCreator());
            ThreadViewHolder.replied.setText(REPLIED);
        }

        //Set the onClickListeners
        ThreadViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, View_Thread_Activity.class); // TODO: change the name of this for the
                intent.putExtra("threads", child_thread.getThreads());
                mctx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return threads.size();
    }


    class ThreadViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture,  profilePicture2;
        TextView text, name, name2, replied;

        public ThreadViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            profilePicture2 = itemView.findViewById(R.id.profilePicture2);

            text = itemView.findViewById(R.id.text);
            name = itemView.findViewById(R.id.name);
            name2 = itemView.findViewById(R.id.name2);
            replied = itemView.findViewById(R.id.replied);
        }
    }
}
