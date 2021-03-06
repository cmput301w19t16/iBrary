package ca.rededaniskal.EntityClasses;

import java.io.Serializable;

public class BorrowRequest extends Request implements Serializable {
    private String isbn;
    private String bookId;

    public BorrowRequest() {
    }

    public BorrowRequest(String senderUID, String recipientUID, String isbn, String bookId) {
        super(senderUID, recipientUID, "BorrowRequest");
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
