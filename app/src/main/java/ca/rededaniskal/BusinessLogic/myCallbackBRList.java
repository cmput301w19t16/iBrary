package ca.rededaniskal.BusinessLogic;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.BorrowRequest;
import ca.rededaniskal.EntityClasses.Display_BorrowRequest;

public interface myCallbackBRList {
    void onCallback(ArrayList<Display_BorrowRequest> borrowRequests);
}
/*
package ca.rededaniskal.BusinessLogic;

import ca.rededaniskal.EntityClasses.BorrowRequest;

public interface myCallbackBookRequest {
    void onCallback(BorrowRequest borrowRequest);
}

 */