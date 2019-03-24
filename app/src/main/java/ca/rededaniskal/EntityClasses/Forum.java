package ca.rededaniskal.EntityClasses;

import java.util.ArrayList;

public class Forum {

    private ArrayList<Thread> threads = new ArrayList<>();
    private Master_Book Book;
    private String forumID;

    public Forum(Book book) {
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
}
