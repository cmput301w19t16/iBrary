package ca.rededaniskal.EntityClasses;

public class Friend_Request extends Request {

    public Friend_Request(String senderUserName, String recipientUserName) {
        super(senderUserName, recipientUserName, "Friend_Request");
    }
}
