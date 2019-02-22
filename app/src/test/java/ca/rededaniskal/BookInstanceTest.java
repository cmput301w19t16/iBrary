package ca.rededaniskal;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

public class BookInstanceTest {

    @Test
    public void TestOwner() {
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available", 5);

        String owner = book.getOwner();

        assertEquals("Jill", owner);
    }

    @Test
    public void TestPossesor() {
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available", 5);

        String possesor = book.getPossesor();

        assertEquals("Jill", possesor);

        book.setPossesor("Rose Edmond");

        possesor = book.getPossesor();

        assertNotEquals("Jill", possesor);
    }

    @Test
    public void TestCondition() {
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available", 5);

        String condition = book.getCondition();

        assertEquals("Perfect!", condition);

        book.setCondition("Cover torn");

        condition = book.getCondition();

        assertNotEquals("Perfect!", condition);
    }

    @Test
    public void TestInstanceId() {
        BookInstance book = new BookInstance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available", 5);

        Integer id = book.getInstanceID();

        assertEquals(5, id);
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