package ca.rededaniskal;

public class Book {
<<<<<<< HEAD
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
=======

    private String title;
    private String author;
    private String isbn;
>>>>>>> 09d679e34079705f54c57f34a6dacb0c44de23f9


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
