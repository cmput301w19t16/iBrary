package ca.refactored.EntityClasses;

import java.util.Date;

public class Notification {
    private Date timestamp;
    private String userID;
    private String requestID;
    private boolean seen;
    private String requestType;


    public Notification(String user, String req, boolean s){
        timestamp = new Date();
        requestID = req;
        userID = user;
        seen = s;
        requestType = "";
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRequest() {
        return requestID;
    }

    public void setRequest(String request) {
        this.requestID = request;
    }

    public boolean getSeen(){ return this.seen;}

    public void setSeen(Boolean s){this.seen = s;}
}
