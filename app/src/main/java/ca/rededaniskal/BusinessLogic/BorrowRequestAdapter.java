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
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.R;

import static android.support.constraint.Constraints.TAG;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class BorrowRequestAdapter extends RecyclerView.Adapter<BorrowRequestAdapter.BorrowRequestViewHolder>{
    public Context mctx;
    private ArrayList<BorrowRequest> list; //List of Requests

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
        borrowRequestViewHolder.requestInfo.setText( request.getSenderUserName());
        borrowRequestViewHolder.bookInfo.setText( request.getBookId() );

        //Set onClick listeners
        borrowRequestViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB add a book request to the database


                Log.d(TAG, "*********------> I JUST DONT KNOW");
                Log.d(TAG, "*********------> I JUST DONT KNOW: "+request.getSenderUserName());
//                list.remove(borrowRequestViewHolder.getAdapterPosition());
//                notifyItemRemoved(borrowRequestViewHolder.getAdapterPosition());
//                notifyItemRangeChanged(borrowRequestViewHolder.getAdapterPosition(), list.size());
                for (int j = 0; j < getItemCount(); j++){
                    if(j == i){
                        request.setStatus("Accepted");
                        updateRequestDB db = new updateRequestDB(request);
                    }else{
                        updateRequestDB db = new updateRequestDB(list.get(j));
                    }
                    list.remove(j);
                    notifyDataSetChanged();
                }
            }
        });

        borrowRequestViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                list.remove(i);
//                notifyDataSetChanged();
//                Log.d(TAG, "************------> Req Object: "+ list.get(i));
                //TODO: DB remove the book request to the database
                request.setStatus("Denied");
                updateRequestDB db = new updateRequestDB(request);
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


    // ---------------- Enclosed DB Class ---------------- //

    private class updateRequestDB{
        private BorrowRequest request;
        private DatabaseReference mDatabase;
        private String key;
        private boolean delete;

        private updateRequestDB(BorrowRequest request) {
            this.request = request;
            Log.d(TAG, "*********------> I JUST DONT KNOW: "+request.getSenderUserName());
            Query query = FirebaseDatabase.getInstance().getReference("BorrowRequests")
                    .orderByChild("isbn")
                    .equalTo(request.getIsbn());

            query.addListenerForSingleValueEvent(valueEventListener);
        }

        private void updateRequest(){
            if(request.getStatus().equals("Accepted")){
                delete = false;
            }else{
                delete = true;
            }
            Log.d(TAG, "*********------> I JUST DONT KEY: "+key);
            mDatabase = FirebaseDatabase.getInstance().getReference("BorrowRequests");
            if(delete){
                mDatabase.child(key).removeValue();
            }else{
                mDatabase.child(key).setValue(request);
            }
        }


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(ContentValues.TAG, "*********----->onDataChangeAdapter");
                if (dataSnapshot.exists()) {
                    Log.d(ContentValues.TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        BorrowRequest req = snapshot.getValue(BorrowRequest.class);
                        if(req.getSenderUserName().equals(request.getSenderUserName())) {
                            key = snapshot.getKey();
                        }
                    }

                    updateRequest();

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


    }


}
