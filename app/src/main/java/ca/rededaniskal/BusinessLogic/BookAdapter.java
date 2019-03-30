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
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Display_Username;
import ca.rededaniskal.R;

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{
    public Activity mctx;
    private ArrayList<Display_Username> bookList;

    /**
     * Instantiates a new Entry adapter.
     */
    public BookAdapter(Activity mctx, ArrayList<Display_Username> bookList) {
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
        View view = inflater.inflate(R.layout.book_list_view, viewGroup, false);
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
        final Display_Username display = bookList.get(i);
        final Book_Instance book = display.getBook();
        //Set the book attributes
        bookViewHolder.bookTitle.setText(book.getTitle());
        bookViewHolder.bookAuthor.setText(book.getAuthor());
        bookViewHolder.bookISBN.setText(book.getISBN());
        bookViewHolder.bookStatus.setText(book.getStatus());
        bookViewHolder.bookOwner.setText(display.getOwner());
        bookViewHolder.bookPosessor.setText(display.getBorrower());

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

        ImageView imageView;
        TextView bookTitle, bookAuthor, bookISBN, bookStatus, bookOwner, bookPosessor;

        /**
         * Instantiates a new Entry view holder.
         */
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.ProfilePicture); //TODO: Make this display the books image

            bookTitle = itemView.findViewById(R.id.title);
            bookAuthor = itemView.findViewById(R.id.author);
            bookISBN = itemView.findViewById(R.id.isbn);
            bookStatus = itemView.findViewById(R.id.viewStatus);
            bookOwner = itemView.findViewById(R.id.viewOwner);
            bookPosessor = itemView.findViewById(R.id.viewPosessor);
        }
    }
}
