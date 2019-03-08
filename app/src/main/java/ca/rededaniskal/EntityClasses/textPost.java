package ca.rededaniskal.EntityClasses;

import ca.rededaniskal.EntityClasses.Post;

public class textPost extends Post {

    public textPost(String message,
                      String username, String ISBN){
        super(message, username, ISBN, "textPost");
    }
}
