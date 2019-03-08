package ca.rededaniskal;

import org.junit.Test;

import ca.rededaniskal.EntityClasses.FriendNotification;
import ca.rededaniskal.EntityClasses.FriendRequest;
import ca.rededaniskal.EntityClasses.Request;

import static org.junit.Assert.*;

public class FriendNotificationTest {

    @Test
    public void requestType(){
        FriendNotification friendNotification = new FriendNotification();
        friendNotification.setUsername("dlothian");

        Request request = new FriendRequest("dlothian", "daniela");
        friendNotification.setRequest(request);
        friendNotification.requestType();
        String requestType = friendNotification.getFriendType();
        assertEquals("requested", requestType);

        Request request2 = new FriendRequest("daniela", "dlothian");
        friendNotification.setRequest(request2);
        friendNotification.requestType();
        String requestType2 = friendNotification.getFriendType();
        assertEquals("accepted", requestType2);

    }

    @Test
    public void setMessage(){
        FriendNotification friendNotification = new FriendNotification();
        Request request = new FriendRequest("dlothian", "daniela");
        friendNotification.setRequest(request);
        friendNotification.setFriendType("requested");
        friendNotification.setMessage();
        String expected = friendNotification.getRequest().getSenderUserName() + " has sent you a friend request";
        assertEquals(expected, friendNotification.getMessage());

        FriendNotification friendNotification2 = new FriendNotification();
        Request request2 = new FriendRequest("dlothian", "daniela");
        friendNotification2.setRequest(request2);
        friendNotification2.setFriendType("accepted");
        friendNotification2.setMessage();
        String expected2 = friendNotification2.getRequest().getRecipientUserName() + " has accepted your friend request";
        assertEquals(expected2, friendNotification2.getMessage());

    }


}
