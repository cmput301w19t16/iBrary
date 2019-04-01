package ca.rededaniskal.EntityClasses;

public class Display_Comment {

    private Comment comment;
    private String displayName;

    public Display_Comment(Comment comment, String displayName) {
        this.comment = comment;
        this.displayName = displayName;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
