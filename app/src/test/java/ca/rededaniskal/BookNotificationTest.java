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
        Request request = new Request("dlothian", "daniela", "friend");
        bookNotification.setRequest(request);
        if(bookNotification.getRequest().getSenderUserName().equals(bookNotification.getUsername())){
            bookNotification.requestType();
            String requestType = bookNotification.getFriendType();
            assertEquals("requested", requestType);
        }else if(bookNotification.getRequest().getRecipientUserName().equals(bookNotification.getUsername())){
            bookNotification.requestType();
            String requestType = bookNotification.getFriendType();
            assertEquals("accepted", requestType);
        }

    }

    @Test
    public void setMessage(){
        BookNotification bookNotification = new BookNotification();
        Request request = new Request("dlothian", "daniela", "friend");
        bookNotification.setRequest(request);
        bookNotification.setFriendType("requested");
        bookNotification.setMessage();
        String expected = bookNotification.getRequest().getSenderUserName() + " has sent you a friend request";
        assertEquals(expected, bookNotification.getMessage());

        BookNotification bookNotification2 = new BookNotification();
        Request request2 = new Request("dlothian", "daniela", "friend");
        bookNotification2.setRequest(request2);
        bookNotification2.setFriendType("accepted");
        bookNotification2.setMessage();
        String expected2 = bookNotification2.getRequest().getRecipientUserName() + " has accepted your friend request";
        assertEquals(expected2, bookNotification2.getMessage());

    }


}
