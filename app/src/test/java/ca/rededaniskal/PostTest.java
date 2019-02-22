package ca.rededaniskal;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class PostTest {
    @Test
    public void TestGetAndSetMessage() {
        User user = new User("alex chan", "a@c.ca", "Edmonton");

        Post post = new Post("this is a post", user.getUserName());

        assertEquals("this is a post", post.getMessage());

        post.setMessage("this a changed post");

        assertEquals("this is a changed post", post.getMessage());
    }

    @Test
    public void TestGetAndSetDate() {
        User user = new User("alex chan", "123@321.ca", "Calgary");

        Post post = new Post("this is a post", user.getUserName());

        Date date = new Date();

        assertEquals(date, post.getDate());
    }

}
