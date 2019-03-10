package ca.rededaniskal.BusinessLogic;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.Activities.Fragments.Notifications_Fragment;
import ca.rededaniskal.Activities.Fragments.Post_Feed_Fragment;
import ca.rededaniskal.Activities.View_Rating_Post_Activity;
import ca.rededaniskal.Activities.View_Text_Post_Activity;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.R;

public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.Notification_View_Holder> {
    private ArrayList<Notification> mDataset;
    public Notifications_Fragment fragment;

    public class Notification_View_Holder extends RecyclerView.ViewHolder{
        public TextView postTitle;
        public RatingBar newAlertStar;
        public View view;
        public String requestType;

        public Notification_View_Holder(View v){
            super(v);
            view = v;
            postTitle = v.findViewById(R.id.notification_text);
            newAlertStar = v.findViewById(R.id.alertStar);
        }
    }

    public Notification_Adapter(ArrayList<Notification> notificationList, Notifications_Fragment frag){
        this.fragment = frag;
        this.mDataset = notificationList;
    }

    @Override
    public Notification_Adapter.Notification_View_Holder onCreateViewHolder (ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.notifications_card_layout, parent, false);

        Notification_View_Holder vh = new Notification_View_Holder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Notification_View_Holder holder, final int position){
        Notification notification = mDataset.get(position);
        //String titleText = notification.getUsername();
        String titleText = "Someone ";
        if (notification.getSeen()){
            holder.newAlertStar.setRating(1);
        }
        else{
            holder.newAlertStar.setRating(0);
        }

        switch (notification.getRequestType()){
            case "Friend_Request":
                titleText += " sent you a friend request!";
                break;
            case "Borrow_Request":
                titleText += " asked to borrow your book.";
                break;
            case "Return_Request":
                titleText += " wants to return your book.";
                break;
            default:
                titleText = "This notification is not displaying correctly.";
                break;
        }

        holder.postTitle.setText(titleText);
        holder.requestType = notification.getRequestType();

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.newAlertStar.setRating(0);
                mDataset.get(position).setSeen(true);
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
    public void addAll(List<Notification> list) {
        mDataset.addAll(list);
        this.notifyDataSetChanged();
    }

    public void checkEmpty(){
        if (mDataset.size() == 0){
            Notification dummy = new Notification("You", "idnumber", true);
            dummy.setRequestType("Other");
            mDataset.add(dummy);
        }
        return;
    }

}
