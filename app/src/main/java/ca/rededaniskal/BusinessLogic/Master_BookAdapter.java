package ca.rededaniskal.BusinessLogic;
//Created by Revan on 2019-03-25

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Forum_Activity;
import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.EntityClasses.Forum;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.R;

/**
 * This is an adapter used to return information about master book
 *
 * @since 2019-03-25
 * @author Revan
 */

//Code was adapted from the code present in tutorial at link https://www.youtube.com/watch?v=Vyqz_-sJGFk
public class Master_BookAdapter extends RecyclerView.Adapter<Master_BookAdapter.Master_BookViewHolder>{
    public Search_Fragment fragment;
    private ArrayList<Master_Book> books;


    /**
     * Instantiates a new Entry adapter.
     */
    public Master_BookAdapter(Search_Fragment ctx,   ArrayList<Master_Book> books) {
        this.fragment = ctx;
        this.books = books;
    }

    /**
     * When View Holder is created
     *
     */
    @NonNull
    @Override
    public Master_BookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout


        View view = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.master_book_card, viewGroup, false);

        Master_BookViewHolder holder = new Master_BookViewHolder(view);
        return holder;
    }

    /**
     * Binds an Entry to a view holder.
     *
     * @param master_BookViewHolder      the the view to be bound to
     * @param i                 position of Entry in list
     */
    @Override
    public void onBindViewHolder(@NonNull Master_BookViewHolder  master_BookViewHolder, final int i) {
        final Master_Book book = books.get(i);

        //Set the Book attributes
        master_BookViewHolder.author.setText(book.getAuthor());
        master_BookViewHolder.title.setText(book.getTitle());
        master_BookViewHolder.isbn.setText(book.getISBN());

        if (book.getAvgRating() != null){
            master_BookViewHolder.rating.setRating(book.getAvgRating());
        }

        //if User clicks on a Master_Book, will start the Master_Book details Activity
        master_BookViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), View_All_Books_Activity.class); // TODO: change the name of this for the
                intent.putExtra("master_book", book);
                fragment.startActivity(intent);
            }
        });

        //Go to the forum
        master_BookViewHolder.goToForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(fragment.getContext(), Forum_Activity.class); // TODO: change the name of this for the
                intent.putExtra("isbn", book.getISBN());
                fragment.startActivity(intent);
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
        if (books == null){
            return 0;
        }

        return books.size();
    }

    /**
     * The type Entry view holder, the object to actually hold an entry
     */
    class Master_BookViewHolder extends RecyclerView.ViewHolder {

        TextView title, author, isbn;
        RatingBar rating;
        Button goToForum;

        /**
         * Instantiates a new Entry view holder.
         */
        public Master_BookViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title);
            author = itemView.findViewById(R.id.showauthor);
            isbn = itemView.findViewById(R.id.showisbn);
            rating = itemView.findViewById(R.id.rating);
            goToForum = itemView.findViewById(R.id.GoToForum);
        }
    }
}
