/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing Notifications
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.Activities.Fragments.Notifications_Fragment;
import ca.rededaniskal.Activities.User_Details_Activity;
import ca.rededaniskal.Activities.View_Book_Request_Activity;
import ca.rededaniskal.Activities.View_Exchange_Details_Activity;
import ca.rededaniskal.Database.BookExchangeDb;
import ca.rededaniskal.Database.BorrowRequestDb;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.Database.Write_Notification_DB;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.view.View.GONE;

//Author: Nick
public class Notification_Adapter extends RecyclerView.Adapter<Notification_Adapter.Notification_View_Holder> {
    private ArrayList<Notification> mDataset;
    public Notifications_Fragment fragment;

    //private String titleText;



    public class Notification_View_Holder extends RecyclerView.ViewHolder{
        private Intent intent;
        private Exchange book_exchange;
        private BorrowRequest borrowRequest;
        private Notification notification;

        private User user;
        private User currentUser;
        private String uid;
        private Users_DB udb;
        private myCallbackUser mcbu;
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
        this.mDataset = new ArrayList<>();
        for (int i = notificationList.size() - 1; i >= 0; i--) {
            this.mDataset.add(notificationList.get(i));
        }

        //this.mDataset = notificationList;
    }

    @Override
    public Notification_Adapter.Notification_View_Holder onCreateViewHolder (ViewGroup parent, int viewType){
        //Set the layout
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.notifications_card_layout, parent, false);

        Notification_View_Holder vh = new Notification_View_Holder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final Notification_View_Holder holder, final int position){
        // Binds an item to the view
        holder.notification = mDataset.get(position);
        removeCard(holder, position);

        holder.uid = holder.notification.getUserID();
        holder.udb = new Users_DB();

        holder.mcbu = new myCallbackUser() {
            @Override
            public void onCallback(User u) {
                holder.currentUser = u;
                getSenderUser(holder, position);
            }
        };

        holder.udb.getUser(holder.uid, holder.mcbu);

        if (!holder.notification.getSeen()){
            holder.newAlertStar.setRating(1);
        }
        else{
            holder.newAlertStar.setRating(0);
        }

        //set the text of the notification based on the type
        holder.requestType = holder.notification.getRequestType();
    }

    private void getSenderUser(final Notification_View_Holder holder, final int position) {
        Users_DB udb = new Users_DB();
        myCallbackUser mcbu = new myCallbackUser() {
            @Override
            public void onCallback(User us) {
                holder.user = us;
                getCardValues(holder, position);
            }
        };
        udb.getUser(holder.notification.getSender(), mcbu);
    }

    private void getCardValues(final Notification_View_Holder holder, final int position){
        String ntype = holder.notification.getRequestType();
        Log.d("This notification's id is: ", holder.notification.getRequestID());
        if (ntype.equals("Book Request Accepted") || ntype.equals("Return_Request")) {
            //TODO: get book exchange from db.
            //given a notification, retrieve the bookexchange
            BookExchangeDb bedb = new BookExchangeDb();
            myCallbackExchange mcbbe = new myCallbackExchange() {
                @Override
                public void onCallback(Exchange be) {
                    holder.book_exchange = be;
                    addCard(holder, position);
                    setCardValues(holder, position);
                }
            };

            bedb.getBookExchange(holder.notification.getRequestID(), mcbbe);
        }
        else if (ntype.equals("Book Requested")){
            BorrowRequestDb brdb = new BorrowRequestDb();
            myCallbackBookRequest mcbr = new myCallbackBookRequest() {
                @Override
                public void onCallback(BorrowRequest br) {
                    holder.borrowRequest = br;
                    addCard(holder, position);
                    setCardValues(holder, position);
                }
            };
            brdb.getBookRequest(holder.notification.getRequestID(), mcbr);
        }
        else if (ntype.equals("Friend Request")){
            addCard(holder, position);
            setCardValues(holder, position);
        }
    }

    private void removeCard(Notification_View_Holder holder, int position){
        holder.newAlertStar.setVisibility(GONE);
        holder.postTitle.setVisibility(GONE);
    }

    private void addCard(Notification_View_Holder holder, int position){
        holder.newAlertStar.setVisibility(View.VISIBLE);
        holder.postTitle.setVisibility(View.VISIBLE);
    }

    private void setCardValues(final Notification_View_Holder holder, final int position){
        String titleText = holder.user.getUserName();

        switch (holder.notification.getRequestType()){
            case "Book Request Accepted":
                titleText += " accepted your book request.";
                holder.intent = new Intent(fragment.getActivity(), View_Exchange_Details_Activity.class);
                holder.intent.putExtra("exchange", holder.book_exchange);
                //intent.putExtra()
                break;
            case "Friend Request":
                titleText += " is now following you.";
                holder.intent = new Intent(fragment.getActivity(), User_Details_Activity.class);
                holder.intent.putExtra("user", holder.user);
                break;
            case "Book Requested":
                titleText += " asked to borrow your book.";
                holder.intent = new Intent(fragment.getActivity(), View_Book_Request_Activity.class);
                holder.intent.putExtra("request", holder.borrowRequest);
                holder.intent.putExtra("Returning", false);
                break;
            case "Return_Request":
                titleText += " wants to return your book.";
                holder.intent = new Intent(fragment.getActivity(), View_Exchange_Details_Activity.class);

                break;
            default:
                titleText = "This notification is not displaying correctly.";
                break;
        }

        //Set the on click listener (for when users click on a notification to silence it)
        if (titleText != "This notification is not displaying correctly.") {
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.newAlertStar.setRating(0);
                    mDataset.get(position).setSeen(true);
                    Write_Notification_DB db = new Write_Notification_DB();
                    db.setRequestID(mDataset.get(position).getRequestID());
                    db.setNotification(mDataset.get(position));
                    db.getNotificationKey();
                    fragment.getActivity().startActivity(holder.intent);
                }
            });
        }
        holder.postTitle.setText(titleText);
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
            Notification dummy = new Notification("You", "Me", "idnumber", true);
            dummy.setRequestType("Other");
            mDataset.add(dummy);
        }
        return;
    }
}
