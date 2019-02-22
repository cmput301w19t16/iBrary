/*
* Since CheckIn and CheckOut both inherit from BookExchange, there will only be tests run for check in for the methods
* that are not abstract or child class specific.
* */


package ca.rededaniskal;

import java.util.Date;

public class ChecksTest {

    public String Location() {
        return location;
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
}
