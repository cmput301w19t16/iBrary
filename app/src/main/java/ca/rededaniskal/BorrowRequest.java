package ca.rededaniskal;

public class BorrowRequest extends Request {
    private String isbn;
    private int bookId;

    public BorrowRequest(String senderUserName, String recipientUserName, String isbn, int bookId) {
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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
