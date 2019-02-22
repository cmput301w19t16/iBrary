package ca.rededaniskal;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class NotificationTest {

    @Test
    public void Timestamp() {
        Date date = new Date();
        Notification notification = new Notification();
        notification.setTimestamp(date);
        Date date1 = notification.getTimestamp();
        assertNotEquals(date, date1);
    }


    @Test
    public void Username() {
        String username = "dlothian";
        Notification notification = new Notification();
        notification.setUsername(username);
        String username1 = notification.getUsername();
        assertNotEquals(username, username1);
    }

    @Test
    public void Request() {
        Request request = new Request();
        Notification notification = new Notification();
        notification.setRequest(request);
        Request request1 = notification.getRequest();
        assertNotEquals(request, request1);
    }

}

