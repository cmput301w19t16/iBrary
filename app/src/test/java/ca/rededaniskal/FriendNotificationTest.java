package ca.rededaniskal;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.junit.Assert.*;

public class FriendNotificationTest {

    @Test
    public void requestType(){
        FriendNotification friendNotification = new FriendNotification("friend");
        friendNotification.setUsername("dlothian");
        Request request = new Request("dlothian", "daniela", "friend");
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
    public void acceptedMessage(){

    }

    @Test
    public void requestedMessage(){

    }
}
