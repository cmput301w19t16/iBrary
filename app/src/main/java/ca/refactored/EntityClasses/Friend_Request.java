package ca.refactored.EntityClasses;

public class Friend_Request extends Request {

    public Friend_Request(String senderUID, String recipientUID) {
        super(senderUID, recipientUID, "Friend_Request");
    }
}
