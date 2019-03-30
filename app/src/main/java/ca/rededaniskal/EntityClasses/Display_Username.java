package ca.rededaniskal.EntityClasses;

public class Display_Username {
    private Book_Instance book;
    private String owner;
    private String borrower;

    public Display_Username(Book_Instance book, String owner, String borrower) {
        this.book = book;
        this.owner = owner;
        this.borrower = borrower;
    }

    public Display_Username(Book_Instance book) {
        this.book = book;
    }


    public Book_Instance getBook() {
        return book;
    }

    public void setBook(Book_Instance book) {
        this.book = book;
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
}
