package ca.rededaniskal;

public class Book {

    private String title;
    private String author;
    private String isbn;


    public Book(String newTitle, String newAuthor, String newIsbn){
        title = newTitle;
        author = newAuthor;
        isbn = newIsbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String newAuthor) {
        author = newAuthor;
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String newIsbn) {
        isbn = newIsbn;
    }
}
