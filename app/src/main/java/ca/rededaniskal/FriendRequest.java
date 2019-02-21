package ca.rededaniskal;

public class FriendRequest extends Request {

    public FriendRequest(String senderUserName, String recipientUserName) {
        super(senderUserName, recipientUserName, "FriendRequest");
    }
}
