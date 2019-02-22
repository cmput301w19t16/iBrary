package ca.rededaniskal;

public class Book {
    private String Title;
    private String Author;
    private String ISBN;
    private String Description;

    public Book(String title, String author, String isbn, String description){
        Title = title;
        Author = author;
        ISBN = isbn;
        Description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
