package ca.rededaniskal;

import java.util.Date;

public class Post {
    private String message;
    private Date date;
    private String userName;

    public Post(String message, String userName) {
        this.message = message;
        this.date = new Date();
        this.userName = userName;
    }

    public Post(String message, Date date, String userName) {
        this.message = message;
        this.date = date;
        this.userName = userName;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}