package ca.rededaniskal;

import org.junit.Test;

import static org.junit.Assert.*;

public class ForumTest {

    @Test
    public void AddPost() {
        Forum forumPosts = new Forum();

        Post post = new Post("First Forum Post Test", "Alex Chan");
        forumPosts.addPost(post);
        assertTrue(forumPosts.hasPost(post));
    }

    @Test
    public void DeletePost() {
        Forum forumPosts = new Forum();

        Post post = new Post("Second Forum Post Test", "Alex Chan");
        forumPosts.deletePost(post);
        assertFalse(forumPosts.hasPost(post));
    }
}