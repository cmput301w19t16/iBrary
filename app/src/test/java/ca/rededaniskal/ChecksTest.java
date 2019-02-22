/*
* Since CheckIn and CheckOut both inherit from BookExchange, there will only be tests run for check in for the methods
* that are not abstract or child class specific.
* */


package ca.rededaniskal;

import java.util.Date;
import static org.junit.Assert.*;

public class ChecksTest {

    // The following tests test the getters and setters for the attributes declared in BookExchange

    public void Location() {
        CheckIn checkin = new CheckIn("dlothian", "daniela", "123456", 7);
        checkin.setLocation("Edmonton, AB");
        String loc = checkin.getLocation();
        assertEquals("Edmonton, AB", loc);
    }


    public void Time() {
        CheckIn checkin = new CheckIn("dlothian", "daniela", "123456", 7);
        Date time = new Date();
        checkin.setTime(time);
        Date timetest = checkin.getTime();
        assertEquals(time, timetest);
    }


    public void Owner() {
        CheckIn checkin = new CheckIn("dlothian", "daniela", "123456", 7);
        String owner = checkin.getOwner();
        assertEquals("dlothian", owner);
        checkin.setOwner("revanMac");
        String owner2 = checkin.getOwner();
        assertEquals("revan", owner2);
    }


    public void Borrower() {
        CheckIn checkin = new CheckIn("dlothian", "daniela", "123456", 7);
        String borrower = checkin.getBorrower();
        assertEquals("daniela", borrower);
        checkin.setBorrower("revanMac");
        String borrower2 = checkin.getBorrower();
        assertEquals("revan", borrower2);
    }


    public void Isbn() {
        CheckIn checkin = new CheckIn("dlothian", "daniela", "123456", 7);
        String isbn = checkin.getIsbn();
        assertEquals("123456", isbn);
        checkin.setIsbn("54321");
        String isbn2 = checkin.getIsbn();
        assertEquals("revan", isbn2);
    }


    public void Bookid() {
        CheckIn checkin = new CheckIn("dlothian", "daniela", "123456", 7);
        int bookid = checkin.getBookid();
        assertEquals(7, bookid);
        checkin.setBookid(8);
        int bookid2 = checkin.getBookid();
        assertEquals(8, bookid2);
    }


    public void scanISBN(){}
}
