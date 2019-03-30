package ca.rededaniskal.Database;

import com.google.firebase.database.DatabaseReference;

import java.util.List;


import ca.rededaniskal.EntityClasses.Forum;
//import ca.rededaniskal.EntityClasses.Text_Post;


public class ForumDb extends Entity_Database {

    public ForumDb() {
        super();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FORUM.reference());
    }

    public void addForum(Forum forum){}

    public void addParentThread(){}
}
