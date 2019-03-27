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

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Database.Write_Request_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

import static android.support.constraint.Constraints.TAG;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class BorrowRequestAdapter extends RecyclerView.Adapter<BorrowRequestAdapter.BorrowRequestViewHolder>{
    public Context mctx;
    private ArrayList<BorrowRequest> list; //List of Requests
    private Write_Request_DB db;

    /**
     * Instantiates a new Entry adapter.
     */
    public BorrowRequestAdapter(Context mctx,  ArrayList<BorrowRequest> list) {
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
        View view = inflater.inflate(R.layout.request_card, null);
        BorrowRequestViewHolder holder = new BorrowRequestViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final BorrowRequestViewHolder borrowRequestViewHolder, final int i) {
        final BorrowRequest request = list.get(i);

        //Set Fields
        borrowRequestViewHolder.requestInfo.setText( request.getsenderUID());
        borrowRequestViewHolder.bookInfo.setText( request.getBookId() );

        //Set onClick listeners
        borrowRequestViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                request.setStatus("Accepted");
                db = new Write_Request_DB(request, false);
                list.remove(borrowRequestViewHolder.getAdapterPosition());
                deleteRemainingRequests();
                list.clear();
                notifyDataSetChanged();
            }
        });

        borrowRequestViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setStatus("Denied");
                Write_Request_DB db = new Write_Request_DB(request, true);
                list.remove(borrowRequestViewHolder.getAdapterPosition());
                notifyItemRemoved(borrowRequestViewHolder.getAdapterPosition());
                notifyItemRangeChanged(borrowRequestViewHolder.getAdapterPosition(), list.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteRemainingRequests(){
        for(int i = 0; i < list.size(); i++){
            Write_Request_DB db = new Write_Request_DB(list.get(i), true);
        }
    }

    /**
     * The type Entry view holder, the obbject to actually hold an entry
     */
    class BorrowRequestViewHolder extends RecyclerView.ViewHolder {

        ImageView profilePic;
        TextView requestInfo;
        TextView bookInfo;
        ImageButton accept, cancel;

        public BorrowRequestViewHolder(@NonNull View itemView) {
            //TODO: profile pic
            super(itemView);
            requestInfo = itemView.findViewById(R.id.requestInfo);
            accept = itemView.findViewById(R.id.accept);
            cancel = itemView.findViewById(R.id.cancel);
            bookInfo = itemView.findViewById(R.id.bookInfo);
        }
    }

}
