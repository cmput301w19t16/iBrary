package ca.rededaniskal;

import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class BookListTests {

    @Test
    public void addBook(){
        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        assertTrue(list.getBooks().contains(book));


    }
    @Test
    public void removeBook(){
        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        assertTrue(list.getBooks().contains(book));
        list.removeBook(book);
        assertFalse(list.getBooks().contains(book));
    }
    @Test
    public void getBookByIndex(){
        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        BookInstance book2 = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book2);

        assertEquals(book, list.getBookByIndex(0));
        assertEquals(book2, list.getBookByIndex(1));
    }
    @Test
    public void getBooks(){

        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        BookInstance book2 = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book2);

        ArrayList<BookInstance> testlist = new ArrayList<>();
        testlist.add(book);
        testlist.add(book2);

        assertEquals(testlist, list.getBooks());
    }
    @Test
    public void getBooksByStatus(){

        String status = "accepted";

        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        BookInstance book2 = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        list.addBook(book);
        list.addBook(book2);

        assertTrue(list.getBooksByStatus(status).contains(book));
        assertFalse(list.getBooksByStatus(status).contains(book2));
    }
    @Test
    public void size(){

        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        BookInstance book2 = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        list.addBook(book);
        list.addBook(book2);

        assertEquals((Integer)2 , list.size());

    }
    @Test
    public void clear(){
        BookList list = new BookList();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        BookInstance book = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        BookInstance book2 = new BookInstance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        list.addBook(book);
        list.addBook(book2);

        assertEquals((Integer)2 , list.size());

        list.clear();

        assertEquals((Integer)0 , list.size());

    }
}
