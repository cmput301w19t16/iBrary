package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.Date;

public abstract class Book_Exchange implements Serializable {
    private Double lat;
    private Double lng;

    private Date time;
    private String owner;
    private String borrower;
    private String isbn;
    private String bookid;

    public Book_Exchange(String owner, String borrower, String isbn, String bookid, Double lat,
                         Double lng, Date time) {
        this.owner = owner;
        this.borrower = borrower;
        this.isbn = isbn;
        this.bookid = bookid;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
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

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public void scanISBN(){}

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public abstract void updateBookStatus();
}
