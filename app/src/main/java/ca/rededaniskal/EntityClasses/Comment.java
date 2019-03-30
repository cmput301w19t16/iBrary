package ca.rededaniskal.EntityClasses;
//Created by Revan on 2019-03-29

public class Comment {

    private String creator;
    private String text;

    public Comment(String creator, String text) {
        this.creator = creator;
        this.text = text;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
