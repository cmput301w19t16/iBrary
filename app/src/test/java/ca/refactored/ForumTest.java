package ca.refactored;

import org.junit.Test;

import ca.refactored.EntityClasses.Book;
import ca.refactored.EntityClasses.Forum;
import ca.refactored.EntityClasses.Post;
import ca.refactored.EntityClasses.User;

import static org.junit.Assert.*;

public class ForumTest {

    @Test
    public void AddPost() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890");

        User user = new User("Bob SMith", "bob@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");
        forumPosts.addPost(post);
        assertTrue(forumPosts.hasPost(post));
    }

    @Test
    public void DeletePost() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890");

        User user = new User("JOe SMith", "joe@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");
        forumPosts.deletePost(post);
        assertFalse(forumPosts.hasPost(post));
    }

    @Test
    public void TestAddReply() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890");

        User user = new User("Bob SMith", "bob@123.ca", "Edmonton");

        User user2 = new User("james smith", "james@123.ca", "Calgary");

        Forum forumPosts = new Forum(book, user.getUserName());

        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");
        forumPosts.addPost(post);

        Post reply = new Post("this is a post", user.getUserName(), "333", "Friend");
        forumPosts.addPost(reply);

        assertEquals("@james smith Reply post test", reply.getMessage());
    }

    @Test
    public void TestForumID() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890");

        User user = new User("james bond", "jb@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        String forumID = forumPosts.getForumID();

        assertEquals("1234567890", forumID);
    }

    @Test
    public void TestGetUsername() {
        Book book = new Book("Happy Pooter", "J.K. Rowling", "1234567890");

        User user = new User("james bond", "jb@123.ca", "Edmonton");

        Forum forumPosts = new Forum(book, user.getUserName());

        assertEquals("james bond", forumPosts.getUserName());
    }

}
