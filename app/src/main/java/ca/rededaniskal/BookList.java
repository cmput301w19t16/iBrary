package ca.rededaniskal;

import java.util.ArrayList;

public class BookList {
    private ArrayList<BookInstance> books;

    public BookList(){
        books = new ArrayList<>();
    }

    public void addBook(BookInstance newBook){
        books.add(newBook);
    }

    public void removeBook(BookInstance removeBook){
        books.remove(removeBook);
    }

    public BookInstance getBookByIndex(Integer ind){
        return books.get(ind);
    }

    public ArrayList<BookInstance> getBooks(){
        return books;
    }

    public ArrayList<BookInstance> getBooksByStatus(String status){
        ArrayList<BookInstance> returnBooks = new ArrayList<>();
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
