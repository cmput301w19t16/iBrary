package ca.rededaniskal;

import java.util.Date;

public class Notification {
    private Date timestamp;
    private String username;
    private Request request;


    public void Notification(){
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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
