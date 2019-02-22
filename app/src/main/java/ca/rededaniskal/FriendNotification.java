package ca.rededaniskal;

public class FriendNotification extends Notification {
    private String friendType;
    private String message;


    public FriendNotification(){
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
