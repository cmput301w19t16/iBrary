package ca.rededaniskal.BusinessLogic;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.Display_BorrowRequest;

public interface myCallbackDBRList {
    void onCallback(ArrayList<Display_BorrowRequest> borrowRequests);
}
