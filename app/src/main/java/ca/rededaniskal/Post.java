package ca.rededaniskal;

import java.util.Date;

public class Post {
    private String message;
    private Date date;
    private String userName;
    private String replyTarget;

    // Constructor for a post
    public Post(String message, String userName) {
        this.message = message;
        this.date = new Date();
        this.userName = userName;
    }

    // Constructor for a reply (has additional end replyTarget)
    public Post(String message, String userName, String replyTarget) {
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }

    public String getUserName() {
        return userName;
    }

    public String getReplyTarget() {
        return replyTarget;
    }

}
