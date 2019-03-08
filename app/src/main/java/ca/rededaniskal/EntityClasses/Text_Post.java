package ca.rededaniskal.EntityClasses;

import ca.rededaniskal.EntityClasses.Post;

public class Text_Post extends Post {

    public Text_Post(String message,
                     String username, String ISBN){
        super(message, username, ISBN, "Text_Post");
    }
}
