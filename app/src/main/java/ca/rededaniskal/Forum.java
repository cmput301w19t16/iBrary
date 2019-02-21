package ca.rededaniskal;

import java.util.ArrayList;

public class Forum {

    private ArrayList<Post> postList = new ArrayList<>();

    public void addPost(Post post) {
        postList.add(post);
    }
}
