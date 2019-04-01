package ca.rededaniskal.BusinessLogic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.R;

import static android.support.constraint.Constraints.TAG;

/**
 * This is an adapter that is used to
 */

public class brAdapter extends RecyclerView.Adapter<brAdapter.brViewHolder> {
        private ArrayList<Book_Instance>  finalList;
        public Activity mctx;

        public brAdapter(Activity mctx,
                         ArrayList<Book_Instance> borrowedBooks,
                         ArrayList<Book_Instance> requestedBooks,
                         Boolean borrowedWanted,
                         Boolean requestedWanted) {

            this.mctx = mctx;
            this.finalList = new ArrayList<>();
            if (borrowedWanted) {
                for (Book_Instance bi : borrowedBooks) {
                    finalList.add(bi);
                }
            }
            if (requestedWanted) {
                for (Book_Instance bi : requestedBooks) {
                    finalList.add(bi);
                }
            }
            for (Book_Instance bi : finalList) {
                Log.d(TAG, "brAdapter: Book Title: " + bi.getTitle());
            }
        }

    @NonNull
    @Override
    public brViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.book_list_view, viewGroup, false);
        brViewHolder holder = new brViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull brViewHolder brViewHolder, final int i) {
        final Book_Instance book = finalList.get(i);
        //Set the book attributes
        brViewHolder.bookTitle.setText(book.getTitle());
        brViewHolder.bookAuthor.setText(book.getAuthor());
        brViewHolder.bookISBN.setText(book.getISBN());
        brViewHolder.bookStatus.setText(book.getStatus());

        if (book.getCover() != null || book.getCover() != "") {
            LoadImage loader = new LoadImage(brViewHolder.bookCover);
            loader.execute(book.getCover());
        }

        //if User clicks on a Book, will start the book details Activity
        brViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, Book_Details_Activity.class); // TODO: change the name of this for the
                intent.putExtra("book", book);
                mctx.startActivity(intent);
                mctx.finish();
            }
        });
    }


    @Override
    public int getItemCount() {
        if (finalList == null) {
            return 0;
        }

        return finalList.size();
    }

    class brViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCover;

        TextView bookTitle, bookAuthor, bookISBN, bookStatus, bookOwner, bookPosessor, possessor, owner;

        public brViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);

            bookTitle = itemView.findViewById(R.id.Title);
            bookAuthor = itemView.findViewById(R.id.author);
            bookISBN = itemView.findViewById(R.id.isbn);
            bookStatus = itemView.findViewById(R.id.viewStatus);
            bookOwner = itemView.findViewById(R.id.viewOwner);
            bookPosessor = itemView.findViewById(R.id.viewPosessor);
            bookCover = itemView.findViewById(R.id.cover);
            possessor = itemView.findViewById(R.id.possesor);
            owner = itemView.findViewById(R.id.owner);

            /*
            bookOwner.setVisibility(View.GONE);
            bookPosessor.setVisibility(View.GONE);
            itemView.findViewById(R.id.possesor).setVisibility(View.GONE);
            itemView.findViewById(R.id.owner).setVisibility(View.GONE);*/
            bookOwner.setTextColor(View.GONE);
            bookPosessor.setTextColor(View.GONE);
            possessor.setTextColor(View.GONE);
            owner.setTextColor(View.GONE);

        }
    }
}
