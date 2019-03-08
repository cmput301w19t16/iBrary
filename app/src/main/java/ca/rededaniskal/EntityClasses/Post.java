package ca.rededaniskal.EntityClasses;

import java.util.UUID;

import java.util.Date;

public class Post {
    private String message;
    private Date timestamp;
    private String userName;
    private String ISBN;
    private String postID;
    private String type;

    // Constructor for a post
    public Post(String message, String userName, String ISBN, String TYPE) {
        this.message = message;
        this.timestamp = new Date();
        this.userName = userName;
        this.ISBN = ISBN;
        this.type = TYPE;
    }


    public String getISBN(){ return ISBN;}

    public String getType(){ return type;}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPostID(String postID){
        this.postID = postID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setID(String id) {this.postID = id;}

    public String getID() {return this.postID;}


}
