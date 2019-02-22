package ca.rededaniskal;

public class FriendNotification extends Notification {
    private String friendType;


    public FriendNotification(String type){
        super();
        this.friendType = type;
    }

    public String getFriendType() {
        return friendType;
    }

    public void setFriendType(String friendType) {
        this.friendType = friendType;
    }

    public void requestType(){}

    public void acceptedMessage(){

    }

    public void requestedMessage(){

    }

}
