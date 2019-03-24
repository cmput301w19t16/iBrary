package ca.rededaniskal.EntityClasses;

import android.accessibilityservice.GestureDescription;

import java.util.ArrayList;

public class Forum {

    private ArrayList<Thread> threads = new ArrayList<>();
    private Master_Book Book;
    private String forumID;
    private ArrayList<String> titles = new ArrayList<>();

    public Forum(Master_Book book) {
        Book = book;
        this.forumID = book.getISBN();
    }

    public void addPost(Thread newThread) {
        threads.add(newThread);
    }

    public boolean hasPost(Post post) {
        return threads .contains(post);
    }

    public ArrayList<Thread> getThreads(){
        return this.threads;
    }

    public String getForumID() {
        return forumID;
    }

    public String getBookName(){
        return Book.getTitle();
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public void addTitle(String string){
        this.titles.add(string);
    }
}
