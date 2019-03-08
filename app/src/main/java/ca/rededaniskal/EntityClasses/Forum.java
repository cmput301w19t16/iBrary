package ca.rededaniskal.EntityClasses;

import java.util.ArrayList;

public class Forum {

    private ArrayList<Post> posts = new ArrayList<>();
    private Post newPost;
    private MasterBook Book;
    private String forumID;
    private String userName;

    public Forum(Book book, String userName) {
        this.forumID = book.getISBN();
        this.userName = userName;

    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public boolean hasPost(Post post) {
        return posts.contains(post);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }

    public String getForumID() {
        return forumID;
    }

    public String getUserName() {
        return userName;
    }

}
