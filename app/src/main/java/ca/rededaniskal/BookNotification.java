package ca.rededaniskal;

public class BookNotification extends Notification {

    private String bookRequestType;
    private String message;

    public BookNotification(){
    }

    public String getFriendType() {
        return bookRequestType;
    }

    public void setFriendType(String bookRequestType) {
        this.bookRequestType = bookRequestType;
    }

    public void requestType(){}

    public String getMessage() {
        return message;
    }

    public void setMessage() {}

    public void acceptedMessage(){}

    public void requestedMessage(){}

    public void borrowReturnedMessage(){}

    public void ownerReturnedMessage(){}
}
