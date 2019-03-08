package ca.rededaniskal.EntityClasses;

public class Friend_Notification extends Notification {
    private String friendType;
    private String message;


    public Friend_Notification(){
    }

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
    }

    public void requestType(){}

    public String getMessage() {
        return message;
    }

    public void setMessage() {
    }

    public void acceptedMessage(){

    }

    public void requestedMessage(){

    }

}
