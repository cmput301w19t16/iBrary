package ca.rededaniskal;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

public class BookNotificationTest {

    @Test
    public void requestType(){
        BookNotification bookNotification = new BookNotification();
        bookNotification.setUsername("dlothian");
        Request request = new BorrowRequest("dlothian", "daniela", "1234", 2);
        request.setStatus("Accepted");
        bookNotification.setRequest(request);
        bookNotification.requestType();
        String requesttype = bookNotification.getBookRequestType();
        assertEquals("Accepted", requesttype);


        bookNotification.getRequest().setStatus("Denied");
        bookNotification.setRequest(request);
        bookNotification.requestType();
        String requesttype2 = bookNotification.getBookRequestType();
        assertEquals("Denied", requesttype2);

        bookNotification.getRequest().setStatus("Pending");
        bookNotification.setRequest(request);
        bookNotification.requestType();
        String requesttype3 = bookNotification.getBookRequestType();
        assertEquals("Pending", requesttype3);


    }

    @Test
    public void setMessage(){
        BookNotification bookNotification = new BookNotification();
        bookNotification.setUsername("dlothian");
        Request request = new BorrowRequest("dlothian", "daniela", "1234", 2);
        request.setStatus("Accepted");
        bookNotification.setRequest(request);
        bookNotification.requestType();
        bookNotification.setMessage();
        String expected1 = bookNotification.getRequest().getRecipientUserName() + " has accepted your book request";
        assertEquals(expected1, bookNotification.getMessage());

        bookNotification.getRequest().setStatus("Denied");
        bookNotification.setRequest(request);
        bookNotification.requestType();
        bookNotification.setMessage();
        String expected2 = bookNotification.getRequest().getRecipientUserName() + " has denied your book request";
        assertEquals(expected2, bookNotification.getMessage());

        bookNotification.getRequest().setStatus("Pending");
        bookNotification.requestType();
        bookNotification.setMessage();
        String expected3 = bookNotification.getRequest().getSenderUserName() + " has request your book";
        assertEquals(expected3, bookNotification.getMessage());

    }


}
