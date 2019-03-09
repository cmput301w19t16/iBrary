package ca.rededaniskal.EntityClasses;

/**
 * Represents a notification related to friends
 * This notification type will be shown when a user wishes to add a friend or accept a friend request
 *
 * @see Notification
 */
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
