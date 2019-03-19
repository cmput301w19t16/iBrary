package ca.rededaniskal;

import org.junit.Test;

import java.util.Date;

import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.User;

import static org.junit.Assert.*;

public class PostTest {
    @Test
    public void TestGetAndSetMessage() {
        User user = new User("alex chan", "a@c.ca", "Edmonton");

        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");

        assertEquals("this is a post", post.getMessage());

        post.setMessage("this is a changed post");

        assertEquals("this is a changed post", post.getMessage());
    }

    @Test
    public void TestGetAndSetTimestamp() {
        User user = new User("alex chan", "123@321.ca", "Calgary");

        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");

        Date timestamp = new Date();
        assertEquals(timestamp, post.getTimestamp());

//        post.setTimestamp("2019-12-31");
//        assertFalse(timestamp, post.getTimestamp());
    }

    @Test
    public void TestGetandSetUsername() {
        User user = new User("alex chan", "123@321.ca", "calgart");

        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");

        assertEquals("alex chan", post.getUserName());

        post.setUserName("john poop");
        assertEquals("john poop", post.getUserName());
    }

//    @Test
//    public void TestGetAndSetReply() {
//        User user = new User("john smith", "js@js.ca", "toronto");
//
//        User user2 = new User("bob jones", "bj@bs.ca", "toronton");
//
//        Post post = new Post("this is a post", user.getUserName(), "333", "Friend");
//
//        assertEquals("bob jones", user2.getUserName());
//
//        post.setReplyTarget("james cameron");
//        assertEquals("james cameron", post.getReplyTarget());
//
//    }
}
