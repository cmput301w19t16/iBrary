package ca.rededaniskal;

//import java.awt.image.*;

public class BookInstance extends Book {

    private String owner;
    private String possesor;
    private String condition;
    //private BufferedImage bookImage;
    private Integer instanceID;
    private String status;


    public BookInstance (String newTitle, String newAuthor, String newIsbn, String newOwner, String newPossesor, String newCondition, String newStatus, Integer newInstanceId){
        super(newTitle, newAuthor, newIsbn);
        owner = newOwner;
        possesor = newPossesor;
        condition = newCondition;
        status = newStatus;
        instanceID = newInstanceId;
    }

    public String getOwner() {
        return owner;
    }

    public String getPossesor() {
        return possesor;
    }
    public void setPossesor(String newPosessor) {
        possesor = newPosessor;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getInstanceID() {
        return instanceID;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    }

    public String getStatus(){
        return status;
    }

}
