package ca.rededaniskal;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

public class BookInstanceTest {
    @Test
    public void TestTitle() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setTitle("Harry Potter");
        String title = book.getTitle();

        assertEquals("Harry Potter", title);

        book.setTitle("Twilight");

        title = book.getTitle();

        assertNotEquals("Harry Potter", title);
    }


    @Test
    public void TestAuthor() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setAuthor("John Smith");
        String author = book.getAuthor();

        assertEquals("John Smith", author);

        book.setTitle("William Jones");

        author = book.getTitle();

        assertNotEquals("John Smith", author);
    }

    @Test
    public void TestISBN() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setISBN("978-3-16-148410-0");
        String isbn = book.getISBN();

        assertEquals("978-3-16-148410-0", isbn);

        book.setISBN("978-3-16-148410-1");

        isbn = book.getISBN();

        assertNotEquals("978-3-16-148410-0", isbn);
    }

    @Test
    public void TestDescription() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setDescription("Coolest book ever!");
        String description = book.getDescription();

        assertEquals("Coolest book ever!", description);

        book.setDescription("Worst read ever!");

        description = book.getDescription();

        assertNotEquals("Coolest book ever!", description);
    }

    @Test
    public void TestStatus() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setStatus("Requested");
        String status = book.getStatus();

        assertEquals("Requested", status);

        book.setStatus("Borrowed");

        status = book.getStatus();

        assertNotEquals("Requested", status);
    }

    @Test
    public void TestOwner() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setOwner("Paul Rick");
        String owner = book.getOwner();

        assertEquals("Paul Rick", owner);

        book.setOwner("Jane Ruth");

        owner = book.getOwner();

        assertNotEquals("Paul Rick", owner);
    }

    @Test
    public void TestBorrower() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setBorrower("Josh Hill");
        String borrower = book.getBorrower();

        assertEquals("Josh Hill", borrower);

        book.setBorrower("Rose Edmond");

        borrower = book.getBorrower();

        assertNotEquals("Josh Hill", borrower);
    }

    @Test
    public void TestBookID() {
        BookInstance book = new BookInstance("John Joe", "Accepted", 12);

        book.setBookId(5);
        int receivedID = book.getBookId();


        assertEquals(5, receivedID);

        book.setBookId(500);

        receivedID = book.getBookId();

        assertNotEquals(5, receivedID);
    }
}