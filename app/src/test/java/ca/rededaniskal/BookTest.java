package ca.rededaniskal;

import org.junit.Test;
import static org.junit.Assert.*;

public class BookTest {

    @Test
    public void TestTitle() {
        Book book = new Book("Programming", "Jack", "978-3-16-148410-0");

        book.setTitle("Harry Potter");
        String title = book.getTitle();

        assertEquals("Harry Potter", title);

        book.setTitle("Twilight");

        title = book.getTitle();

        assertNotEquals("Harry Potter", title);
    }


    @Test
    public void TestAuthor() {
        Book book = new Book("Programming", "Jack", "978-3-16-148410-0");

        String author = book.getAuthor();

        assertEquals("Jack", author);

        book.setAuthor("William Jones");

        author = book.getTitle();

        assertNotEquals("Jack", author);
    }

    @Test
    public void TestISBN() {
        Book book = new Book("Programming", "Jack", "978-3-16-148410-0");

        String isbn = book.getISBN();

        assertEquals("978-3-16-148410-0", isbn);

        book.setISBN("978-3-16-148410-1");

        isbn = book.getISBN();

        assertNotEquals("978-3-16-148410-0", isbn);
    }

}
