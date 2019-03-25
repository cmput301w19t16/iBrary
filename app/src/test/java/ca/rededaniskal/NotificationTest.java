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
        Notification notification = new Notification("dlothian", "accepted", false);
        notification.setTimestamp(date);
        Date date1 = notification.getTimestamp();
        assertEquals(date, date1);
    }


    @Test
    public void Username() {
        String username = "432947239814324";
        Notification notification = new Notification(username, "accepted", false);
        notification.setUserID(username);
        String username1 = notification.getUserID();
        assertEquals(username, username1);
    }

    @Test
    public void Request() {
//        Request request = new Request("dlothian", "daniela", "friend");
        String id = "yes";
//        Notification notification = new Notification();
//        notification.setRequest(id);
//        String request1 = notification.getRequest();
//        assertEquals(id, request1);
    }

}

