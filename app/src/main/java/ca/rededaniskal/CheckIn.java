package ca.rededaniskal;

public class CheckIn extends BookExchange {

    public CheckIn(String owner, String borrower, String isbn, int bookid) {
        super(owner, borrower, isbn, bookid);
    }

    public void updateBookStatus(){}

    public void addBookToBorrowed(){}
}
