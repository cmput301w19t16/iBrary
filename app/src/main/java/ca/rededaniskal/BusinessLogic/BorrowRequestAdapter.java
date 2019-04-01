/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing Borrow requests
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;
//Created by Daniela, Revan

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Establish_Exchange_Details_Activity;
import ca.rededaniskal.Activities.View_Book_Request_Activity;
import ca.rededaniskal.Database.BookInstanceDb;
import ca.rededaniskal.Database.Users_DB;
import ca.rededaniskal.Database.Write_Request_DB;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Display_BorrowRequest;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

/**
 * This is an adapter that is used to set the information about a borrow request
 *
 * @author Daniela, Revan
 */

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class BorrowRequestAdapter extends RecyclerView.Adapter<BorrowRequestAdapter.BorrowRequestViewHolder>{
    public Context mctx;
    private ArrayList<Display_BorrowRequest> list; //List of Requests
    private Write_Request_DB db;
    private User user;
    private Book_Instance bi;
    private BorrowRequest request;
    private BorrowRequestViewHolder holder;

    /**
     * Instantiates a new Entry adapter.
     */
    public BorrowRequestAdapter(Context mctx,  ArrayList<Display_BorrowRequest> list) {
        this.mctx = mctx;
        this.list = list;
    }

    /**
     * When View Holder is created
     *
     */
    @NonNull
    @Override
    public BorrowRequestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the Layout
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.request_card, viewGroup, false);
        BorrowRequestViewHolder holder = new BorrowRequestViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull BorrowRequestViewHolder borrowRequestViewHolder, final int i) {
        Display_BorrowRequest display = list.get(i);
        request = display.getRequest();
        user = display.getUser();
        String urlProfilePic = user.getProfilePic();

        holder = borrowRequestViewHolder;
        if(urlProfilePic != null){
            LoadImage loader = new LoadImage(holder.bookCover);
            loader.execute(urlProfilePic);
        }

        //Set Fields
        
        if (request.getsenderUID() != null) {
            getUserInfo(request.getsenderUID());
            getBookInfo(request.getrecipientUID(), request.getBookId());
        }
        else{
            borrowRequestViewHolder.requestInfo.setText( request.getsenderUID());
            holder.bookInfo.setText( request.getBookId() );
        }


        //Set onClick listeners
        borrowRequestViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request.setStatus("Accepted");

                /* On accepted, all other requests are deleted except the accepted, which
                Which is passed to the Establish_Exchange_Details_Activity */
                deleteRemainingRequests();

                Intent intent = new Intent(mctx,Establish_Exchange_Details_Activity.class);
                intent.putExtra("BorrowRequestObject", request);
                intent.putExtra("Returning", false);
                mctx.startActivity(intent);
            }
        });

        borrowRequestViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setStatus("Denied");
                Write_Request_DB db = new Write_Request_DB(request, true);
                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
            }
        });
    }

    private void viewRequest(){
        Intent intent = new Intent(mctx, View_Book_Request_Activity.class);
        intent.putExtra("request", request);
        mctx.startActivity(intent);
    }

    private void getUserInfo(String uid){
        Users_DB udb = new Users_DB();
        myCallbackUser mcb = new myCallbackUser() {
            @Override
            public void onCallback(User u) {
                user = u;
                if(user.getUserName()!=null) {
                    fillUserInfo();
                }
            }
        };
        udb.getUser(uid, mcb);
    }

    private void fillUserInfo(){
        holder.requestInfo.setText( user.getUserName());
    }

    private void getBookInfo(String ownerId, String bid){
        BookInstanceDb bidb = new BookInstanceDb();
        myCallbackBookInstance mcbi = new myCallbackBookInstance() {
            @Override
            public void onCallback(Book_Instance bins) {
                bi = bins;
                fillBookInfo();
            }
        };
        bidb.getBookInstance(ownerId, bid, mcbi);
    }

    private void fillBookInfo(){
        holder.bookInfo.setText( bi.getTitle() );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteRemainingRequests(){
        for(int i = 0; i < list.size(); i++){
            Write_Request_DB db = new Write_Request_DB(list.get(i).getRequest(), true);
        }
    }

    /**
     * The type Entry view holder, the object to actually hold an entry
     */
    class BorrowRequestViewHolder extends RecyclerView.ViewHolder {

        ImageView bookCover;
        TextView requestInfo;
        TextView bookInfo;
        ImageButton accept, cancel;

        public BorrowRequestViewHolder(@NonNull View itemView) {
            //TODO: profile pic
            super(itemView);
            requestInfo = itemView.findViewById(R.id.Title);
            accept = itemView.findViewById(R.id.accept);
            cancel = itemView.findViewById(R.id.cancel);
            bookInfo = itemView.findViewById(R.id.bookInfo);

            bookCover = itemView.findViewById(R.id.pic);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //viewRequest();
                }
            });

        }
    }

}
