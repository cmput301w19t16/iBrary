package ca.rededaniskal.BusinessLogic;
//Created by Revan on 2019-03-29

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import ca.rededaniskal.Activities.View_Exchange_Details_Activity;
import ca.rededaniskal.EntityClasses.Book_Exchange;
import ca.rededaniskal.R;

public class Book_ExchangeAdapter extends RecyclerView.Adapter<Book_ExchangeAdapter.Book_ExchangeViewHolder>{

    public static final String REPLIED = "replied";
    public Context mctx;
    private ArrayList<Book_Exchange> Book_Exchanges;

    public Book_ExchangeAdapter(Context mctx, ArrayList<Book_Exchange> Book_Exchanges) {
        this.mctx = mctx;
        this.Book_Exchanges = Book_Exchanges;
    }


    @NonNull
    @Override
    public Book_ExchangeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //Set the layout
        LayoutInflater inflater = LayoutInflater.from(mctx);

        View view = inflater.inflate(R.layout.exchange_card, null);
        Book_ExchangeViewHolder holder = new Book_ExchangeViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull Book_ExchangeViewHolder book_ExchangeViewHolder, final int i) {
        final Book_Exchange exchange = Book_Exchanges.get(i);

        book_ExchangeViewHolder.title.setText(exchange.getBookid()); //TODO: get title from the DB
        book_ExchangeViewHolder.owner.setText(exchange.getOwner());
        book_ExchangeViewHolder.borrower.setText(exchange.getBorrower());

        SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy HH:mm");
        String date =formatter.format(exchange.getTime());
        book_ExchangeViewHolder.time.setText(date);

        book_ExchangeViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mctx, View_Exchange_Details_Activity.class); // TODO: change the name of this for the
                intent.putExtra("exchange", exchange);
                mctx.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return Book_Exchanges.size();
    }

    class Book_ExchangeViewHolder extends RecyclerView.ViewHolder {
        TextView title, owner, borrower, time;
        
        public Book_ExchangeViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.Title);
            owner = itemView.findViewById(R.id.showowner);
            borrower = itemView.findViewById(R.id.showborrower);
            time = itemView.findViewById(R.id.showtime);
        }
    }
}
