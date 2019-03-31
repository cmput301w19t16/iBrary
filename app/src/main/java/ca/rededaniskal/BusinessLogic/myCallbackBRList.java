package ca.rededaniskal.BusinessLogic;

import java.util.ArrayList;

import ca.rededaniskal.EntityClasses.BorrowRequest;

public interface myCallbackBRList {
    void onCallback(ArrayList<BorrowRequest> borrowRequests);
}
/*
package ca.rededaniskal.BusinessLogic;

import ca.rededaniskal.EntityClasses.BorrowRequest;

public interface myCallbackBookRequest {
    void onCallback(BorrowRequest borrowRequest);
}

 */