package ca.refactored;

import org.junit.Test;

import java.util.ArrayList;

import ca.refactored.EntityClasses.Book_Instance;
import ca.refactored.EntityClasses.Book_List;
import ca.refactored.EntityClasses.User;

import static org.junit.Assert.*;

public class BookListTests {

    @Test
    public void addBook(){
        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        assertTrue(list.getBooks().contains(book));


    }
    @Test
    public void removeBook(){
        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        assertTrue(list.getBooks().contains(book));
        list.removeBook(book);
        assertFalse(list.getBooks().contains(book));
    }
    @Test
    public void getBookByIndex(){
        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book2);

        assertEquals(book, list.getBookByIndex(0));
        assertEquals(book2, list.getBookByIndex(1));
    }
    @Test
    public void getBooks(){

        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book);

        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        list.addBook(book2);

        ArrayList<Book_Instance> testlist = new ArrayList<>();
        testlist.add(book);
        testlist.add(book2);

        assertEquals(testlist, list.getBooks());
    }
    @Test
    public void getBooksByStatus(){

        String status = "accepted";

        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        list.addBook(book);
        list.addBook(book2);

        assertTrue(list.getBooksByStatus(status).contains(book));
        assertFalse(list.getBooksByStatus(status).contains(book2));
    }
    @Test
    public void size(){

        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        list.addBook(book);
        list.addBook(book2);

        assertEquals((Integer)2 , list.size());

    }
    @Test
    public void clear(){
        Book_List list = new Book_List();

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        list.addBook(book);
        list.addBook(book2);

        assertEquals((Integer)2 , list.size());

        list.clear();

        assertEquals((Integer)0 , list.size());

    }
}
