package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.ArrayList;

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
