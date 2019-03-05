package ca.rededaniskal;

//import java.awt.image.*;

import android.app.DownloadManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class BookInstance extends Book implements Serializable {

    private String owner;
    private String possessor;
    private String condition;
    //private BufferedImage bookImage;

    private String status;
    private ArrayList<Request> requests;

    public BookInstance (String newTitle, String newAuthor, String newIsbn, String newOwner, String newpossessor, String newCondition, String newStatus){
        super(newTitle, newAuthor, newIsbn);
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

    //Returns boolean if that user has sent a request for a given book
    public boolean isRequestedBy(User user){
        boolean isRequested = false;
        for (Integer i  = 0; i< requests.size(); i++){
            if (requests.get(i).getSenderUserName().equals(user.getUserName())){
                isRequested = true;
                return isRequested;
            }
        }
        return isRequested;
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
