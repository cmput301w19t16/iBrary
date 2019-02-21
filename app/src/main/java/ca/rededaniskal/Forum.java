package ca.rededaniskal;

import java.util.ArrayList;

public class Forum {

    private ArrayList<Post> posts = new ArrayList<>();

    public void addPost(Post post) {
        posts.add(post);
    }

    public boolean hasPost(Post post) {
        return posts.contains(post);
    }

    public void deletePost(Post post) {
        posts.remove(post);
    }
}
