package ca.rededaniskal;

public class Book {
    private String Title;
    private String Author;
    private String ISBN;
    private String Description;

    public Book(String Title, String Author, String ISBN, String Description) {
        this.Title = Title;
        this.Author = Author;
        this.ISBN = ISBN;
        this.Description = Description;
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
