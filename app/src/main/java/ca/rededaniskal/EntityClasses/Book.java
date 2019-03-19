package ca.rededaniskal.EntityClasses;
import java.io.Serializable;
import java.util.UUID;


public class Book implements Serializable {

    private String title;
    private String author;
    private String isbn;
    private String id;

    public Book(){

    }

    public Book(String newTitle, String newAuthor, String newIsbn){
        title = newTitle;
        author = newAuthor;
        isbn = newIsbn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
