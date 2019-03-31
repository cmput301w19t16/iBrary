package ca.rededaniskal.Database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import ca.rededaniskal.Activities.Forum_Activity;
import ca.rededaniskal.BusinessLogic.myCallbackUidList;
import ca.rededaniskal.EntityClasses.Forum;

import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.EntityClasses.Thread;


public class ForumDb extends Entity_Database  {
    private Forum_Activity parent;
    private ArrayList<Thread> threads;
    private Thread T;
    private myCallbackUidList muidList;
    private String ISBN;



    public ForumDb(Forum_Activity p, String isbn) {
        super();
        parent = p;
        ISBN = isbn;
        threads = new ArrayList<>();
    }

    @Override
    public DatabaseReference getReference() {
        return db.getReference(References.FORUM.reference());
    }
    public void syncData(String isbn){
        getReference().child(isbn).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                parent.refresh(dataSnapshot.getValue(Thread.class));



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    public void addParentThread(Thread thread, String isbn){
        T = thread;

        muidList = new myCallbackUidList() {
            @Override
            public void onCallback(ArrayList<String> uidList) {
                updateFollowers(uidList);
            }
        };
        getReference().child(isbn).push().setValue(thread);

        Follow_DB follow_db = new Follow_DB();
        follow_db.getFollowers(thread.getCreator(),muidList);




    }
    public void updateFollowers(ArrayList<String> followers){
        if (T!=null){
        Post p = new Post(T.getText(), T.getCreator(), ISBN, T.getTopic());
        for (String follower: followers) {
            db.getReference(References.FEED.reference()).child(follower).push().setValue(p);
        }}

    }

    public void getThreads(String isbn){
        threads.clear();
        Query q = getReference().child(isbn).limitToFirst(100);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    threads.add(d.getValue(Thread.class));
                }
                parent.loadThreads(threads);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }







}
