package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represents a list of books
 *
 * @see Book_Instance
 */
public class Book_List implements Serializable {
    private ArrayList<Book_Instance> books;

    public Book_List(){
        books = new ArrayList<>();
    }

    public void addBook(Book_Instance newBook){
        books.add(newBook);
    }

    public void removeBook(Book_Instance removeBook){
        books.remove(removeBook);
    }

    public Book_Instance getBookByIndex(Integer ind){
        return books.get(ind);
    }

    public ArrayList<Book_Instance> getBooks(){
        return books;
    }

    /**
     * Returns all books with the given status
     *
     * @param status    the status of the book requested
     * @return a list of books with the specified status
     * @see Book_Instance
     */
    public ArrayList<Book_Instance> getBooksByStatus(String status){
        ArrayList<Book_Instance> returnBooks = new ArrayList<>();
        for (Integer i = 0; i < books.size(); i++){
            if (books.get(i).getStatus() == status){
                returnBooks.add(books.get(i));
            }
        }

        return returnBooks;
    }

    public Integer size(){
        return books.size();
    }

    public void clear(){
        books.clear();
    }

}
