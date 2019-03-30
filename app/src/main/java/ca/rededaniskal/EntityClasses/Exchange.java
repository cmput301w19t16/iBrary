package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.Date;

public class Exchange extends Book_Exchange implements Serializable {

    public Exchange(String owner, String borrower, String isbn, String bookid, Double lat,
                    Double lng, Date time) {

        super(owner, borrower, isbn, bookid, lat, lng, time);
    }

    public Exchange(){}

    public void updateBookStatus(){}

    public void addBookToBorrowed(){}
    // These lists are in User profile

}
