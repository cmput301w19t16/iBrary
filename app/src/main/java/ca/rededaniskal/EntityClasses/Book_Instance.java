package ca.rededaniskal.EntityClasses;

//import java.awt.image.*;

import java.io.Serializable;

public class Book_Instance extends Book implements Serializable {

    private String owner;
    private String possessor;
    private String condition;
    private String bookID;

    //private BufferedImage bookImage;

    private String status;

    public Book_Instance(){

    }




    public Book_Instance(String newTitle, String newAuthor, String newIsbn, String newOwner, String newpossessor, String newCondition, String newStatus){
        super(newTitle, newAuthor, newIsbn);
        this.bookID = null;
        this.owner = newOwner;
        this.possessor = newpossessor;
        this.condition = newCondition;
        this.status = newStatus;




    }

    public void setBookID(String key) {
       this.bookID =key;

    }

    public String getBookID() {
        if (bookID!=null){
        return bookID;}
        else return "";
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
