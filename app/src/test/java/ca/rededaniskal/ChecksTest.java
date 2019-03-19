/*
* Since Check_In and Check_Out both inherit from Book_Exchange, there will only be tests run for check in for the methods
* that are not abstract or child class specific.
* */


package ca.rededaniskal;
import java.util.Date;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Check_In;
import ca.rededaniskal.EntityClasses.Check_Out;
import ca.rededaniskal.EntityClasses.User;

import static org.junit.Assert.*;

public class ChecksTest {

    // The following tests test the getters and setters for the attributes declared in Book_Exchange

    public void Location() {
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        checkin.setLocation("Edmonton, AB");
        String loc = checkin.getLocation();
        assertEquals("Edmonton, AB", loc);
    }


    public void Time() {
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        Date time = new Date();
        checkin.setTime(time);
        Date timetest = checkin.getTime();
        assertEquals(time, timetest);
    }


    public void Owner() {
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        String owner = checkin.getOwner();
        assertEquals("dlothian", owner);
        checkin.setOwner("revanMac");
        String owner2 = checkin.getOwner();
        assertEquals("revan", owner2);
    }


    public void Borrower() {
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        String borrower = checkin.getBorrower();
        assertEquals("daniela", borrower);
        checkin.setBorrower("revanMac");
        String borrower2 = checkin.getBorrower();
        assertEquals("revan", borrower2);
    }


    public void Isbn() {
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        String isbn = checkin.getIsbn();
        assertEquals("123456", isbn);
        checkin.setIsbn("54321");
        String isbn2 = checkin.getIsbn();
        assertEquals("revan", isbn2);
    }


    public void Bookid() {
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        int bookid = checkin.getBookid();
        assertEquals(7, bookid);
        checkin.setBookid(8);
        int bookid2 = checkin.getBookid();
        assertEquals(8, bookid2);
    }

    // Tests related to the methods unique to Check_In

    public void updateBookStatusCheckIn(){
        User user = new User("dlothian", "d@lot.com", "Edmonton, AB");
        Check_In checkin = new Check_In("dlothian", "daniela", "123456", 7);
        Book_Instance bookinst = new Book_Instance("Happy Potter", "JK Rowling", "12334", "dlothian", "daniela", "so niiice", "available");
        user.addOwnedBook(bookinst);
        checkin.updateBookStatus();
        assertEquals("available", bookinst.getStatus());
    }

    public void addBookToBorrowed(){
        User user = new User("daniela", "d@lot.com", "Edmonton, AB");
        Book_Instance bookinst = new Book_Instance("Happy Potter", "JK Rowling", "12334", "dlothian", "daniela", "so niiice", "available");
        user.addBorrowedBook(bookinst);
        assertEquals(bookinst, user.getBorrowedBooks().getBookByIndex(0));
    }

    // Tests related to the methods unique to Check_Out

    public void updateBookStatusCheckOut(){
        User user = new User("dlothian", "d@lot.com", "Edmonton, AB");
        Check_Out checkout = new Check_Out("dlothian", "daniela", "123456", 7);
        Book_Instance bookinst = new Book_Instance("Happy Potter", "JK Rowling", "12334", "dlothian", "daniela", "so niiice", "available");
        user.addOwnedBook(bookinst);
        checkout.updateBookStatus();
        assertEquals("unavailable", bookinst.getStatus());
    }

    public void removeBookFromBorrowed(){
        User user = new User("dlothian", "d@lot.com", "Edmonton, AB");
        Check_Out checkout = new Check_Out("dlothian", "daniela", "123456", 7);
        Book_Instance bookinst = new Book_Instance("Happy Potter", "JK Rowling", "12334", "dlothian", "daniela", "so niiice", "available");
        user.addOwnedBook(bookinst);
        checkout.removeBookFromBorrowed();
        assertEquals((Integer)0,user.getBorrowedBooks().size());
    }


    public void scanISBN(){}
}
