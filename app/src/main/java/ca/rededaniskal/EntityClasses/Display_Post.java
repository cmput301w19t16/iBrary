package ca.rededaniskal.EntityClasses;

public class Display_Post {
    private Post post;
    private String poster;

    public Display_Post(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
