package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.Date;

public class Check_In extends Book_Exchange implements Serializable {

    public Check_In(String owner, String borrower, String isbn, String bookid, Double lat,
                    Double lng, Date time) {

        super(owner, borrower, isbn, bookid, lat, lng, time);
    }
    public void updateBookStatus(){}

    public void addBookToBorrowed(){}
    // These lists are in User profile
}
