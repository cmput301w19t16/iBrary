package ca.refactored;

import org.junit.Test;

import ca.refactored.EntityClasses.Book_Instance;

import static org.junit.Assert.*;

import static org.junit.Assert.assertTrue;

public class BookInstanceTest {

    @Test
    public void TestBookID(){
        Book_Instance book = new Book_Instance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");
        assertTrue(book.getBookID().getClass().equals(String.class));

    }



    @Test
    public void TestOwner() {
        Book_Instance book = new Book_Instance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String owner = book.getOwner();

        assertEquals("Jill", owner);
    }

    @Test
    public void TestPossessor() {

        Book_Instance book = new Book_Instance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String possessor = book.getPossessor();

        assertEquals("Jill", possessor);

        book.setPossessor("Rose Edmond");

        possessor = book.getPossessor();

        assertNotEquals("Jill", possessor);
    }

    @Test
    public void TestCondition() {

        Book_Instance book = new Book_Instance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String condition = book.getCondition();

        assertEquals("Perfect!", condition);

        book.setCondition("Cover torn");

        condition = book.getCondition();

        assertNotEquals("Perfect!", condition);
    }

    @Test
    public void TestStatus() {

        Book_Instance book = new Book_Instance("Programming", "Jack", "978-3-16-148410-0", "Jill", "Jill", "Perfect!", "Available");

        String status = book.getStatus();

        assertEquals("Available", status);

        book.setStatus("Borrowed");

        status = book.getStatus();

        assertNotEquals("Available", status);
    }


}