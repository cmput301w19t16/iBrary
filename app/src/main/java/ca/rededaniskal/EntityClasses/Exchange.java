package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.Date;

public class Exchange extends Book_Exchange implements Serializable {
    private boolean ownerScanend;
    private boolean borrowedScanned;
    private String exchangeID;
    public Exchange(String owner, String borrower, String isbn, String bookid, Double lat,
                    Double lng, Date time) {

        super(owner, borrower, isbn, bookid, lat, lng, time);
        ownerScanend = false;
        borrowedScanned = false;
    }

    public Exchange(){}

    public String getExchangeID() {
        return exchangeID;
    }

    public void setExchangeID(String exchangeID) {
        this.exchangeID = exchangeID;
    }

    public boolean isOwnerScanend() {
        return ownerScanend;
    }

    public void setOwnerScanend(boolean ownerScanend) {
        this.ownerScanend = ownerScanend;
    }

    public boolean isBorrowedScanned() {
        return borrowedScanned;
    }

    public void setBorrowedScanned(boolean borrowedScanned) {
        this.borrowedScanned = borrowedScanned;
    }

    public void updateBookStatus(){}

    public void addBookToBorrowed(){}
    // These lists are in User profile

}
