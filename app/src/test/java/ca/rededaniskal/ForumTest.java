package ca.rededaniskal;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForumTest {

    @Test
    public void AddPost() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890", "Good condition");

        User user = new User("Bob SMith", "bob@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        Post post = new Post("First Forum Post Test", forumPosts.getUserName());
        forumPosts.addPost(post);
        assertTrue(forumPosts.hasPost(post));
    }

    @Test
    public void DeletePost() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890", "Good condition");

        User user = new User("JOe SMith", "joe@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        Post post = new Post("Second Forum Post Test", forumPosts.getUserName());
        forumPosts.deletePost(post);
        assertFalse(forumPosts.hasPost(post));
    }

    @Test
    public void TestForumID() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890", "Good condition");

        User user = new User("james bond", "jb@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        String forumID = forumPosts.getForumID();

        assertEquals("1234567890", forumID);
    }
}
