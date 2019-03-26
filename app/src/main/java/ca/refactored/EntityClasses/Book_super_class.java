package ca.refactored.EntityClasses;

import java.io.Serializable;
//Created by Revan on 2019-03-26

class Book_super_class  implements Serializable {
    protected String title;
    protected String author;
    protected String isbn;

    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
        title = newTitle;
    }

    public String getTheAuthor() {
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

