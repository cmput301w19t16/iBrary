package ca.rededaniskal.EntityClasses;

public class Check_Out extends Book_Exchange {

    public Check_Out(String owner, String borrower, String isbn, int bookid) {
        super(owner, borrower, isbn, bookid);
    }

    public void updateBookStatus(){}

    public void removeBookFromBorrowed(){}
}
