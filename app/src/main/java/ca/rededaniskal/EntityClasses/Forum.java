package ca.rededaniskal.EntityClasses;

import java.util.ArrayList;

public class Forum {

    private ArrayList<Parent_Thread> threads = new ArrayList<>();
    private Master_Book Book;
    private String forumID;

    public Forum(Master_Book book) {
        Book = book;
        this.forumID = book.getISBN();
    }

    public void addPost(Parent_Thread newThread) {
        threads.add(newThread);
    }

    public ArrayList<Parent_Thread> getThreads(){
        return this.threads;
    }

    public String getForumID() {
        return forumID;
    }

    public String getBookName(){
        return Book.getTitle();
    }

}
