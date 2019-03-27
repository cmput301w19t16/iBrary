package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
    private String senderUserName;
    private String recipientUserName;

    private String requestType;
    private String status;
    private String requestId;
    private Date timestamp;
    private String recipientUID;
    private String senderUID;
    public Request(){}

    public Request(String senderUID, String recipientUID, String type){
        this.senderUID = senderUID;
        this.recipientUID = recipientUID;
        this.requestType = type;
        this.status = "Pending";
        timestamp = new Date();
    }


    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getsenderUID() {
        return senderUID;
    }

    public void setsenderUID(String senderUID) {
        this.senderUID = senderUID;
    }

    public String getrecipientUID() {
        return recipientUID;
    }

    public void setrecipientUID(String recipientUID) {
        this.recipientUID = recipientUID;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void accept(){
        this.setStatus("Accepted");
    }

    public void deny(){
        this.setStatus("Denied");
    }

    public void cancel(){
        this.setStatus("Cancelled");
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
