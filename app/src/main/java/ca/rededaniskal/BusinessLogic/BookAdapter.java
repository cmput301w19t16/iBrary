/* TYPE:
 * Adapter
 *
 * PURPOSE:
 * Adapter for viewing Book lists
 *
 * ISSUES:
 * Display images from DB
 */
package ca.rededaniskal.BusinessLogic;
// Created by Revan on 2019-03-03

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Database.Photos;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.R;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    public Activity mctx;
    private Book_List bookList;

    /**
     * Instantiates a new Entry adapter.
     */
    public BookAdapter(Activity mctx, Book_List bookList) {
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
        //Set the layout
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
        final Book_Instance book = bookList.getBookByIndex(i);

        //Set the book attributes
        bookViewHolder.bookTitle.setText(book.getTitle());
        bookViewHolder.bookAuthor.setText(book.getAuthor());
        bookViewHolder.bookISBN.setText(book.getISBN());
        bookViewHolder.bookStatus.setText(book.getStatus());
        bookViewHolder.bookOwner.setText(book.getOwner());
        if(book.getCover() != null){
            Bitmap bitmap = new Photos(mctx).getBitmapFromURL(book.getCover());
            bookViewHolder.bookCover.setImageBitmap(bitmap);
            Log.v("BookAdapter", "getCover not null");
        }

        Log.v("BookAdapterTwo", "getCover null");
        //if User clicks on a Book, will start the book details Activity
        bookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, Book_Details_Activity.class); // TODO: change the name of this for the
                intent.putExtra("book", book);
                mctx.startActivity(intent);
                mctx.finish();
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
        if (bookList ==null){
            return 0;
        }

        return bookList.size();
    }

    /**
     * The type Entry view holder, the object to actually hold an entry
     */
    class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView bookCover;
        TextView bookTitle, bookAuthor, bookISBN, bookStatus, bookOwner;

        /**
         * Instantiates a new Entry view holder.
         */
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.ProfilePicture); //TODO: Make this display the books image

            bookTitle = itemView.findViewById(R.id.username);
            bookAuthor = itemView.findViewById(R.id.Location);
            bookISBN = itemView.findViewById(R.id.BookISBN);
            bookStatus = itemView.findViewById(R.id.bookStatus);
            bookOwner = itemView.findViewById(R.id.bookOwner);
            bookCover = itemView.findViewById(R.id.BookCover);
        }
    }

}
