package ca.rededaniskal.EntityClasses;

import java.util.Date;

public class Check_Out extends Book_Exchange {

    public Check_Out(String owner, String borrower, String isbn, String bookid, Double lat,
                     Double lng, Date time) {

        super(owner, borrower, isbn, bookid, lat, lng, time);
    }
    public void updateBookStatus(){}

    public void removeBookFromBorrowed(){}
}
