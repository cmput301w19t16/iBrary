package ca.rededaniskal.EntityClasses;

public class FriendRequest extends Request {

    public FriendRequest(String senderUserName, String recipientUserName) {
        super(senderUserName, recipientUserName, "FriendRequest");
    }
}
