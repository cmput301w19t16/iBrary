package ca.rededaniskal;

import java.util.Date;

public abstract class  BookExchange {
    private String location;
    private Date time;
    private String owner;
    private String borrower;
    private String isbn;
    private int bookid;

    public BookExchange(String owner, String borrower, String isbn, int bookid) {
        this.owner = owner;
        this.borrower = borrower;
        this.isbn = isbn;
        this.bookid = bookid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public void scanISBN(){}

    public abstract void updateBookStatus();
}
