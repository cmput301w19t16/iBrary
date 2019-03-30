package ca.rededaniskal.EntityClasses;

import java.util.ArrayList;

public class  Forum {

    private ArrayList<Thread> threads = new ArrayList<>();
    private String isbn;

    public Forum(String isbn) {
        this.isbn = isbn;
    }

    public void addPost(Thread newThread) {
        threads.add(newThread);
    }

    public ArrayList<Thread> getThreads(){
        return this.threads;
    }

    public void setThreads(ArrayList<Thread> threads) {
        this.threads = threads;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
