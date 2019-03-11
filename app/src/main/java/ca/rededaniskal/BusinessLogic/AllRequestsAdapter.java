/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing your requests
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;

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

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.R;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class AllRequestsAdapter extends RecyclerView.Adapter<AllRequestsAdapter.AllRequestsViewHolder> {
    public Context mctx;
    private ArrayList<Request> list;

    /**
     * Instantiates a new Entry adapter.
     */
    public AllRequestsAdapter(Context mctx, ArrayList<Request> list) {
        this.mctx = mctx;
        this.list = list;
    }

    /**
     * When View Holder is created
     */
    @NonNull
    @Override
    public AllRequestsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.request_card, null);
        AllRequestsViewHolder holder = new AllRequestsViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull AllRequestsViewHolder allRequestsViewHolder, final int i) {
        final Request request = list.get(i);

        allRequestsViewHolder.bookInfo.setText(request.getSenderUserName());
        allRequestsViewHolder.requestInfo.setText(request.getSenderUserName());

        allRequestsViewHolder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB add a book request to the database

            }
        });

        allRequestsViewHolder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: DB remove the book request to the database
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * The type Entry view holder, the object to actually hold an entry
     */
    class AllRequestsViewHolder extends RecyclerView.ViewHolder {

        ImageView bookCover;
        TextView requestInfo, bookInfo;
        ImageButton accept, cancel;

        public AllRequestsViewHolder(@NonNull View itemView) {
            //TODO: book cover
            super(itemView);
            requestInfo = itemView.findViewById(R.id.requestInfo);
            accept = itemView.findViewById(R.id.accept);
            cancel = itemView.findViewById(R.id.cancel);
            bookInfo = itemView.findViewById(R.id.bookInfo);
        }
    }
}
