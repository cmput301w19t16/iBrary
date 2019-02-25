package ca.rededaniskal;

//import java.awt.image.*;

import android.app.DownloadManager;

import java.util.ArrayList;

public class BookInstance extends Book {

    private String owner;
    private String possessor;
    private String condition;
    //private BufferedImage bookImage;

    private String status;
    private ArrayList<Request> requests;

<<<<<<< HEAD
    public BookInstance (String title, String author, String isbn, String description, String newOwner, String newStatus, Integer newBookId){
        super(title, author, isbn, description);
=======

    public BookInstance (String newTitle, String newAuthor, String newIsbn, String newOwner, String newpossessor, String newCondition, String newStatus){
        super(newTitle, newAuthor, newIsbn);
>>>>>>> 09d679e34079705f54c57f34a6dacb0c44de23f9
        owner = newOwner;
        possessor = newpossessor;
        condition = newCondition;
        status = newStatus;
        requests = new ArrayList<Request>();

    }

    public void addRequest(Request newRequest){
        requests.add(newRequest);
    }


    public void deleteRequest(Request request){
        requests.remove(request);
    }

    public ArrayList<Request> getAllRequests(){
        return requests;

    }

    public String getOwner() {
        return owner;
    }

    public String getPossessor() {
        return possessor;
    }
    public void setPossessor(String newPosessor) {
        possessor = newPosessor;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public String getStatus(){
        return status;
    }



}
