package ca.rededaniskal;

public class CheckOut extends BookExchange {

    public CheckOut(String owner, String borrower, String isbn, int bookid) {
        super(owner, borrower, isbn, bookid);
    }

    public void updateBookStatus(){}

    public void removeBookFromBorrowed(){}
}