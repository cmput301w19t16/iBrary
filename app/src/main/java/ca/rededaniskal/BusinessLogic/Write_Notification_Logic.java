package ca.rededaniskal.BusinessLogic;

import android.util.Log;

import ca.rededaniskal.Database.Write_Notification_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Notification;

import static android.support.constraint.Constraints.TAG;

/**
 * Represents the logic related creating and removing notifications
 */

public class Write_Notification_Logic {

    private Write_Notification_DB db;
    private String UID;
    private String RequestID;
    private String RequestType;
    private String sender;

    public Write_Notification_Logic(String UID, String sender, String RequestID, String RequestType){
        this.UID = UID;
        this.sender = sender;
        this.RequestID = RequestID;
        this.RequestType = RequestType;
        createNotification();
    }

    public Write_Notification_Logic(String RequestID){
        this.RequestID = RequestID;
        removeNotification();
    }

    public void removeNotification(){
        db = new Write_Notification_DB(RequestID);
    }

    public void updateNotification(Notification notification, boolean seen){
        notification.setSeen(seen);

        db = new Write_Notification_DB(notification);
    }

    public void createNotification(){
        Log.d(TAG, "&*&*&* Create Notification sender: " + sender);
        Notification notification = new Notification(UID, sender, RequestID, RequestType);

        db = new Write_Notification_DB(notification);
    }
}
