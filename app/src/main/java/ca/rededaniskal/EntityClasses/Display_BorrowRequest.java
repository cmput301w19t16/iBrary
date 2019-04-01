package ca.rededaniskal.EntityClasses;

public class Display_BorrowRequest {
    private BorrowRequest request;
    private User user;

    public Display_BorrowRequest(BorrowRequest request) {
        this.request = request;
        this.user = user;
    }

    public BorrowRequest getRequest() {
        return request;
    }

    public void setRequest(BorrowRequest request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
