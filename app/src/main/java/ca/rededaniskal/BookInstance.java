package ca.rededaniskal;

//import java.awt.image.*;

public class BookInstance extends Book {

    private String owner;
    private String borrower;
    //private BufferedImage bookImage;
    private String status;
    private int bookId;

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public String getStatus(){
        return status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String newBorrower) {
        this.borrower = newBorrower;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
