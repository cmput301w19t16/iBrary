package ca.rededaniskal;

abstract public class Book {
    private String Title;
    private String Author;
    private String ISBN;
    private String Description;


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

    public abstract void setStatus(String status);
    public abstract String getStatus();

    public abstract void setBorrower(String newBorrower);
    public abstract String getBorrower();

    public abstract int getBookId();
    public abstract void setBookId(int ID);
}
