package ca.rededaniskal.EntityClasses;

/**
 * Represents a borrow request, used to distinct between BorrowRequest
 *
 * @see Request
 * @see Friend_Request
 */
public class Friend_Request extends Request {

    public Friend_Request(String senderUserName, String recipientUserName) {
        super(senderUserName, recipientUserName, "Friend_Request");
    }
}
