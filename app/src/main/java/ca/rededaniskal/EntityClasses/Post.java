package ca.rededaniskal.EntityClasses;

public class Post {
    private String text;
    private String uid;
    private String ISBN;
    private String topic;

    // Constructor for a post
    public Post(String text, String uid, String ISBN, String topic) {
        this.text = text;
        this.uid = uid;
        this.ISBN = ISBN;
        this.topic = topic;
    }

    public Post() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
