package ca.rededaniskal;

import java.util.Date;

public class Notification {
    private Date timestamp;
    private User username;
    private Request request;


    public void Notification(Request request, User username){
        setRequest(request);
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void viewRequest(Request request){
//        Create intent to view request
    }
}
