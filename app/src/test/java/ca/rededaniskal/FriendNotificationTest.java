package ca.rededaniskal;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

public class FriendNotificationTest {

    @Test
    public void requestType(){
        FriendNotification friendNotification = new FriendNotification();
        friendNotification.setUsername("dlothian");
        Request request = new FriendRequest("dlothian", "daniela");
        friendNotification.setRequest(request);
        if(friendNotification.getRequest().getSenderUserName().equals(friendNotification.getUsername())){
            friendNotification.requestType();
            String requestType = friendNotification.getFriendType();
            assertEquals("requested", requestType);
        }else if(friendNotification.getRequest().getRecipientUserName().equals(friendNotification.getUsername())){
            friendNotification.requestType();
            String requestType = friendNotification.getFriendType();
            assertEquals("accepted", requestType);
        }

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
