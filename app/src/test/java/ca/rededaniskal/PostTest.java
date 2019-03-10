package ca.rededaniskal;

import org.junit.Test;

import java.util.Date;

import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.Rating_Post;
import ca.rededaniskal.EntityClasses.Text_Post;
import ca.rededaniskal.EntityClasses.User;

import static org.junit.Assert.*;

public class PostTest {
    @Test
    public void TestRating_Post() {
        User user = new User ("John smith", "js@js.ca", "toronto");

        Rating_Post post = new Rating_Post("message", user.getUserName(), "123", 2.5);

        assertEquals(2.5, post.getRating());

        post.setRating(3.5);

        assertEquals(3.5, post.getRating());

        assertEquals("Rating_Post", post.getType());

        assertEquals("John smith", post.getUserName());
    }

    public void TestText_Post() {
        User user = new User ("John smith", "js@js.ca", "toronto");

        Text_Post post = new Text_Post("message", user.getUserName(), "123");

        assertEquals("Text_Post", post.getType());

        Date timestamp = new Date();
        assertEquals(timestamp, post.getTimestamp());
    }


}
