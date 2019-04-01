package ca.rededaniskal.EntityClasses;
//Created by Revan on 2019-03-25

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Thread implements Serializable {
    private String threadId;

    private String topic;
    private String text;
    private String creator;
    ArrayList<Comment> comments;

    //required no argument constructor
    public Thread(){}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thread thread = (Thread) o;
        return Objects.equals(threadId, thread.threadId) &&
                Objects.equals(topic, thread.topic) &&
                Objects.equals(text, thread.text) &&
                Objects.equals(creator, thread.creator) &&
                Objects.equals(comments, thread.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threadId);
    }

    public Thread(String creator, String text, String topic){
        this.text = text;
        this.creator = creator;
        this.topic = topic;
        comments = new ArrayList<>();
    }

    public void addComment(Comment newComment){
        if (comments ==null){
            comments = new ArrayList<>();
        }

        comments.add(newComment);
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public Integer numComments(){
       if (comments!=null){
        return comments.size();}
        return 0;
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }
}
