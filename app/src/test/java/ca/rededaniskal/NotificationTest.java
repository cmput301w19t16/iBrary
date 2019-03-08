package ca.rededaniskal;

import org.junit.Test;

import java.util.Date;

import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.Request;

import static org.junit.Assert.*;

public class NotificationTest {

    @Test
    public void Timestamp() {
        Date date = new Date();
        Notification notification = new Notification();
        notification.setTimestamp(date);
        Date date1 = notification.getTimestamp();
        assertEquals(date, date1);
    }


    @Test
    public void Username() {
        String username = "dlothian";
        Notification notification = new Notification();
        notification.setUsername(username);
        String username1 = notification.getUsername();
        assertEquals(username, username1);
    }

    @Test
    public void Request() {
        Request request = new Request("dlothian", "daniela", "friend");
        Notification notification = new Notification();
        notification.setRequest(request);
        Request request1 = notification.getRequest();
        assertEquals(request, request1);
    }

}

