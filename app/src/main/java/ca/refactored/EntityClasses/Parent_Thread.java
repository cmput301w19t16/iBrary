package ca.refactored.EntityClasses;
//Created by Revan on 2019-03-25

import java.io.Serializable;

public class Parent_Thread extends Thread implements Serializable {

    String topic;

    public Parent_Thread( String creator, String text , String topic){
        super(creator, text);
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
