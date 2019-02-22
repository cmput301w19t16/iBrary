package ca.rededaniskal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MasterBookTest {

    @Test
    public void TestRating() {
        MasterBook book = new MasterBookTest("Programming", "Jack", "978-3-16-148410-0", 5, "dteodore");

        String status = book.getStatus();

        assertEquals("Available", status);

        book.setStatus("Borrowed");

        status = book.getStatus();

        assertNotEquals("Available", status);
    }

    @Test
    public void TestStatus() {
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available", 5);

        String status = book.getStatus();

        assertEquals("Available", status);

        book.setStatus("Borrowed");

        status = book.getStatus();

        assertNotEquals("Available", status);
    }

}
