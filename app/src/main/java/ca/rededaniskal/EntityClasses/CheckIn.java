package ca.rededaniskal.EntityClasses;

import ca.rededaniskal.EntityClasses.BookExchange;

public class CheckIn extends BookExchange {

    public CheckIn(String owner, String borrower, String isbn, int bookid) {
        super(owner, borrower, isbn, bookid);
    }

    public void updateBookStatus(){}

    public void addBookToBorrowed(){}
    // These lists are in User profile
}
