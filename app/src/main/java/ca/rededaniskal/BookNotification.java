package ca.rededaniskal;

public class BookNotification extends Notification {

    private String bookRequestType;
    private String message;

    public BookNotification(){
    }

    public String getBookRequestType() {
        return bookRequestType;
    }

    public void setBookRequestType(String bookRequestType){
        this.bookRequestType = bookRequestType;
    }

    public void requestType(){
        this.bookRequestType = getRequest().getStatus();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage() {}

    public void acceptedMessage(){}

    public void requestedMessage(){}

    public void borrowReturnedMessage(){}

    public void ownerReturnedMessage(){}
}
