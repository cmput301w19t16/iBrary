package ca.rededaniskal;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RequestTest {
    @Test
    public void getUsernames(){
        String user1 = "Nick";
        String user2 = "Daniela";
        Request request = new FriendRequest(user1, user2);
        assertEquals(user1, request.getSenderUserName());
        assertEquals(user2, request.getRecipientUserName());
    }

    @Test
    public void setUsernames(){
        String user1 = "Nick";
        String user2 = "Daniela";
        Request request = new FriendRequest("mark", "Joe");
        request.setRecipientUserName(user2);
        request.setSenderUserName(user1);
        assertEquals(user1, request.getSenderUserName());
        assertEquals(user2, request.getRecipientUserName());
    }

    @Test
    public void testRequestType(){
        String requestType = "BorrowRequest";
        Request request = new FriendRequest("mark", "Joe");
        assertEquals("FriendRequest", request.getRequestType());
        request.setRequestType(requestType);
        assertEquals(requestType, request.getRequestType());
    }

    @Test
    public void getStatus(){
        String status = "Pending";
        Request request = new FriendRequest("mark", "Joe");
        assertEquals(status, request.getStatus());
    }

    @Test
    public void setStatus(){
        String status = "Something Else";
        Request request = new FriendRequest("mark", "Joe");
        request.setStatus(status);
        assertEquals(status, request.getStatus());
    }

    @Test
    public void Accept(){
        String status = "Accepted";
        Request request = new FriendRequest("mark", "Joe");
        request.accept();
        assertEquals(status, request.getStatus());
    }

    @Test
    public void Deny(){
        String status = "Denied";
        Request request = new FriendRequest("mark", "Joe");
        request.deny();
        assertEquals(status, request.getStatus());
    }

    @Test
    public void Cancel(){
        String status = "Cancelled";
        Request request = new FriendRequest("mark", "Joe");
        request.cancel();
        assertEquals(status, request.getStatus());
    }

    @Test
    public void testRequestId(){
        int requestId = 1;
        Request request = new FriendRequest("mark", "Joe");
        request.setRequestId(requestId);
        assertEquals(requestId, request.getRequestId());
    }

    @Test
    public void testISBN(){
        String ISBN = "10-5";
        BorrowRequest request = new BorrowRequest("mark", "Joe",
                        "2", 15);
        request.setIsbn(ISBN);
        assertEquals(ISBN, request.getIsbn());
    }

    @Test
    public void testBookId(){
        int bookId = 5;
        BorrowRequest request = new BorrowRequest("mark", "Joe",
                "2", 15);
        request.setBookId(bookId);
        assertEquals(bookId, request.getBookId());
    }

}
