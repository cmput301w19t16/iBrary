package ca.rededaniskal;

import org.junit.Test;

import ca.rededaniskal.EntityClasses.Friend_Notification;
import ca.rededaniskal.EntityClasses.Friend_Request;
import ca.rededaniskal.EntityClasses.Request;

import static org.junit.Assert.*;

public class FriendNotificationTest {

    @Test
    public void requestType(){
        Friend_Notification friendNotification = new Friend_Notification();
        friendNotification.setUsername("dlothian");

        Request request = new Friend_Request("dlothian", "daniela");
        friendNotification.setRequest(request);
        friendNotification.requestType();
        String requestType = friendNotification.getFriendType();
        assertEquals("requested", requestType);

        Request request2 = new Friend_Request("daniela", "dlothian");
        friendNotification.setRequest(request2);
        friendNotification.requestType();
        String requestType2 = friendNotification.getFriendType();
        assertEquals("accepted", requestType2);

    }

    @Test
    public void setMessage(){
        Friend_Notification friendNotification = new Friend_Notification();
        Request request = new Friend_Request("dlothian", "daniela");
        friendNotification.setRequest(request);
        friendNotification.setFriendType("requested");
        friendNotification.setMessage();
        String expected = friendNotification.getRequest().getSenderUserName() + " has sent you a friend request";
        assertEquals(expected, friendNotification.getMessage());

        Friend_Notification friendNotification2 = new Friend_Notification();
        Request request2 = new Friend_Request("dlothian", "daniela");
        friendNotification2.setRequest(request2);
        friendNotification2.setFriendType("accepted");
        friendNotification2.setMessage();
        String expected2 = friendNotification2.getRequest().getRecipientUserName() + " has accepted your friend request";
        assertEquals(expected2, friendNotification2.getMessage());

    }


}
