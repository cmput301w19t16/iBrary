package ca.rededaniskal.Database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


import ca.rededaniskal.Activities.Forum_Activity;
import ca.rededaniskal.Activities.View_Thread_Activity;
import ca.rededaniskal.BusinessLogic.myCallbackUidList;

import ca.rededaniskal.EntityClasses.Comment;
import ca.rededaniskal.EntityClasses.Display_Comment;
import ca.rededaniskal.EntityClasses.Display_Thread;
import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.Thread;


public class ForumDb extends Entity_Database  {
    private Forum_Activity parent;
    private View_Thread_Activity kiddo;
    private ArrayList<Thread> threads;
    private ArrayList<Display_Thread> display_threads;
    private ArrayList<Display_Comment> display_comments;
    private Thread T;
    private myCallbackUidList muidList;
    private String ISBN;
    private Comment c;


    public ForumDb(View_Thread_Activity kid, String isbn) {
        super();
        ISBN = isbn;
        threads = new ArrayList<>();
        display_threads =new ArrayList<>();
        display_comments =new ArrayList<>();
        kiddo=kid;


    }
    public ForumDb(Forum_Activity p, String isbn) {
        super();
        parent = p;
        ISBN = isbn;
        threads = new ArrayList<>();
        display_threads = new ArrayList<>();


    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FORUM.reference());
    }




    public void addParentThread(Thread thread){
        T = thread;
        String threadId = getReference().child(ISBN).push().getKey();
        T.setThreadId(threadId);
        getReference().child(ISBN).child(threadId).setValue(T);



        muidList = new myCallbackUidList() {
            @Override
            public void onCallback(ArrayList<String> uidList) {
                updateFollowers(uidList);
            }
        };


        Follow_DB follow_db = new Follow_DB();
        follow_db.getFollowers(thread.getCreator(),muidList);
        getThreads();


    }

    public void updateFollowers(ArrayList<String> followers){
        if (T!=null){
        Post p = new Post(T.getText(), T.getCreator(), ISBN, T.getTopic());
        T=null;
        for (String follower: followers) {
            db.getReference(References.FEED.reference()).child(follower).push().setValue(p);
        }}
        if(c!=null){

            Post p = new Post(c.getText(), c.getCreator(),ISBN, c.getTopic());
            c = null;
            for (String follower: followers) {
                db.getReference(References.FEED.reference()).child(follower).push().setValue(p);
            }


        }

    }

    public void getThreads(){

        threads.clear();
        Query q = getReference().child(ISBN).limitToLast(100);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Thread thread =   d.getValue(Thread.class);

                    getReference().child(ISBN).child(d.getKey()).keepSynced(true);
                    threads.add(thread);
                }
                getThreadDisplayName(threads);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    public void comment(String threadID, Comment comment){
        c = comment;

        getReference().child(ISBN)
                .child(threadID)
                .child("comments")
                .child(String.valueOf(comment.getPos()))
                .setValue(comment);
        muidList = new myCallbackUidList() {
            @Override
            public void onCallback(ArrayList<String> uidList) {
                updateFollowers(uidList);
            }
        };

        Follow_DB follow_db = new Follow_DB();
        follow_db.getFollowers(c.getCreator(),muidList);


    }



public void getCommentsForThread(String threadID){

        getReference().child(ISBN).child(threadID).child("comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Comment> comments = new ArrayList<>();
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    comments.add(d.getValue(Comment.class));

                } 
               getCommentDisplayName(comments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


}




public void getThreadDisplayName(ArrayList<Thread> threds) {
    for (Thread t : threds) {
        final Thread thread = t;
        db.getReference(References.USER.reference()).child(t.getCreator()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    Display_Thread dt = new Display_Thread(thread, dataSnapshot.getValue(String.class));
                    if (dt != null) {
                        Log.d("null", "the whole display thread is null");

                        Log.d("null", "the thread" + thread.getText());
                        Log.d("null", "the String" + dataSnapshot.getValue(String.class));
                        display_threads.add(dt);
                    }
                }
                parent.loadThreads(display_threads);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
    public void getCommentDisplayName(ArrayList < Comment > comments) {
        for (Comment c: comments) {
            final Comment comment = c;
            db.getReference(References.USER.reference()).child(c.getCreator()).child("userName").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        Display_Comment dc = new Display_Comment(comment, dataSnapshot.getValue(String.class));
                        if (dc != null) {
                            Log.d("null", "the whole display thread is null");


                            display_comments.add(dc);
                        }
                    }
                    kiddo.getThreadComments(display_comments);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }


}
