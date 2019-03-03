package ca.rededaniskal;
// Created by Revan on 2019-03-03

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    public Context mctx;
    private BookList bookList;

    /**
     * Instantiates a new Entry adapter.
     */
    public BookAdapter(Context mctx, BookList bookList) {
        this.mctx = mctx;
        this.bookList = bookList;
    }

    /**
     * When View Holder is created
     *
     */
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mctx);
        View view = inflater.inflate(R.layout.book_list_view, null);
        BookViewHolder holder = new BookViewHolder(view);
        return holder;
    }

    /**
     * Binds an Entry to a view holder.
     *
     * @param bookViewHolder      the the view to be bound to
     * @param i                 position of Entry in list
     */
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder  bookViewHolder, final int i) {
        final BookInstance book = bookList.getBookByIndex(i);

        //Set the book attributes
        bookViewHolder.bookTitle.setText(book.getTitle());
        bookViewHolder.bookAuthor.setText(book.getAuthor());
        bookViewHolder.bookISBN.setText(book.getISBN());
        bookViewHolder.bookStatus.setText(book.getStatus());
        bookViewHolder.bookOwner.setText(book.getOwner());

        //if User clicks on a Book, will start the book details Activity
        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(mctx, BookDetailsActivity.class); // TODO: change the name of this for the
                bundle.putSerializable("KEY", book);
                intent.putExtras(bundle); // Pass in the postion of entry to be changed in the list
                mctx.startActivity(intent);
            }
        });
    }

    /**
     * returns the size of the Entry list
     *
     * @return  EntryList.size()
     */
    @Override
    public int getItemCount() {
        return bookList.size();
    }

    /**
     * The type Entry view holder, the obbject to actually hold an entry
     */
    class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView bookTitle, bookAuthor, bookISBN, bookStatus, bookOwner;

        /**
         * Instantiates a new Entry view holder.
         */
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ProfilePicture); //TODO: Make this display the books image

            bookTitle = itemView.findViewById(R.id.UserName);
            bookAuthor = itemView.findViewById(R.id.time);
            bookISBN = itemView.findViewById(R.id.UserMutualFriend);
            bookStatus = itemView.findViewById(R.id.bookStatus);
            bookOwner = itemView.findViewById(R.id.bookOwner);
        }
    }

}
