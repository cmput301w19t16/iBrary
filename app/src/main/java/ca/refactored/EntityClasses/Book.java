package ca.refactored.EntityClasses;
import java.io.Serializable;


public class Book extends Book_super_class {

    private String id;

    public Book(){

    }

    public Book(String newTitle, String newAuthor, String newIsbn){
        title = newTitle;
        author = newAuthor;
        isbn = newIsbn;
    }

}
