/*
* Since Exchange and Check_Out both inherit from Book_Exchange, there will only be tests run for check in for the methods
* that are not abstract or child class specific.
* */


package ca.rededaniskal;
import java.util.Date;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Exchange;
import ca.rededaniskal.EntityClasses.User;

import static org.junit.Assert.*;

public class ChecksTest {

    // The following tests test the getters and setters for the attributes declared in Book_Exchange

    public void Location() {
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        exchange.setLocation("Edmonton, AB");
        String loc = exchange.getLocation();
        assertEquals("Edmonton, AB", loc);
    }


    public void Time() {
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        Date time = new Date();
        exchange.setTime(time);
        Date timetest = exchange.getTime();
        assertEquals(time, timetest);
    }


    public void Owner() {
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        String owner = exchange.getOwner();
        assertEquals("dlothian", owner);
        exchange.setOwner("revanMac");
        String owner2 = exchange.getOwner();
        assertEquals("revan", owner2);
    }


    public void Borrower() {
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        String borrower = exchange.getBorrower();
        assertEquals("daniela", borrower);
        exchange.setBorrower("revanMac");
        String borrower2 = exchange.getBorrower();
        assertEquals("revan", borrower2);
    }


    public void Isbn() {
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        String isbn = exchange.getIsbn();
        assertEquals("123456", isbn);
        exchange.setIsbn("54321");
        String isbn2 = exchange.getIsbn();
        assertEquals("revan", isbn2);
    }


    public void Bookid() {
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        int bookid = exchange.getBookid();
        assertEquals(7, bookid);
        exchange.setBookid(8);
        int bookid2 = exchange.getBookid();
        assertEquals(8, bookid2);
    }

    // Tests related to the methods unique to Exchange

    public void updateBookStatusCheckIn(){
        User user = new User("dlothian", "d@lot.com", "Edmonton, AB");
        Exchange exchange = new Exchange("dlothian", "daniela", "123456", 7);
        Book_Instance bookinst = new Book_Instance("Happy Potter", "JK Rowling", "12334", "dlothian", "daniela", "so niiice", "available");
        user.addOwnedBook(bookinst);
        exchange.updateBookStatus();
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
