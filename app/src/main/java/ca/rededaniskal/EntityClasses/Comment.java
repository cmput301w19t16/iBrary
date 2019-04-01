package ca.rededaniskal.EntityClasses;
//Created by Revan on 2019-03-29

import java.io.Serializable;

public class Comment implements Serializable {

    private String creator;
    private String text;
    private String topic;
    private int pos;
    //required no-argument contructor for database

    public Comment(){}

    public Comment(String creator, String text) {
        this.creator = creator;
        this.text = text;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
