package ca.rededaniskal.EntityClasses;

public class Display_Post {
    private Post post;
    private String poster;
    private String title;

    public Display_Post(Post post) {
        this.post = post;
    }

    public Post getPost() {
        return post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
