package ca.rededaniskal;

import java.util.Date;

public class Post {
    private String message;
    private Date timestamp;
    private String userName;
    private String replyTarget;

    // Constructor for a post
    public Post(String message, String userName) {
        this.message = message;
        this.timestamp = new Date();
        this.userName = userName;
    }

    // Constructor for a reply (has additional end replyTarget)
    public Post(String message, String userName, String replyTarget) {
        this.timestamp = new Date();
        this.userName = userName;
        this.replyTarget = replyTarget;
        this.message = "@" + replyTarget + " " + message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public String getReplyTarget() {
        return replyTarget;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setReplyTarget(String replyTarget) {
        this.replyTarget = replyTarget;
    }
}
