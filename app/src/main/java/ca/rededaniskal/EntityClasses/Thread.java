package ca.rededaniskal.EntityClasses;
//Created by Revan on 2019-03-22

import java.util.ArrayList;

public class Thread {
    private ArrayList<Thread> child_threads = new ArrayList<>();
    private String creator;
    private String text;

    public Thread( String creator, String text ){
        //TODO Authenticate that is forum doesnt already exist
        this.creator = creator;
        this.text = text;
    }

    public ArrayList<Thread> getThreads() {
        return child_threads;
    }

    public void addThread(Thread newThread){
        this.child_threads.add(newThread);
    }

    public String getText() {
        return text;
    }

    public String getCreator() {
        return creator;
    }

}
