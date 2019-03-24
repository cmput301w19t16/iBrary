package ca.rededaniskal.EntityClasses;

import java.io.Serializable;

public class Request implements Serializable {
    private String senderUserName;
    private String recipientUserName;
    private String requestType;
    private String status;
    private String requestId;

    public Request(){}

    public Request(String senderUserName, String recipientUserName, String type){
        this.senderUserName = senderUserName;
        this.recipientUserName = recipientUserName;
        this.requestType = type;
        this.status = "Pending";
    }

    public String getSenderUserName() {
        return senderUserName;
    }

    public void setSenderUserName(String senderUserName) {
        this.senderUserName = senderUserName;
    }

    public String getRecipientUserName() {
        return recipientUserName;
    }

    public void setRecipientUserName(String recipientUserName) {
        this.recipientUserName = recipientUserName;
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
