package ca.rededaniskal.EntityClasses;

import java.io.Serializable;

public class BorrowRequest extends Request implements Serializable {
    private String isbn;
    private String bookId;

    public BorrowRequest() {
    }

    public BorrowRequest(String senderUserName, String recipientUserName, String isbn, String bookId) {
        super(senderUserName, recipientUserName, "BorrowRequest");
        this.bookId = bookId;
        this.isbn = isbn;
    }



    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
