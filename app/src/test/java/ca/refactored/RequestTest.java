package ca.refactored;

import org.junit.Test;

import ca.refactored.EntityClasses.BorrowRequest;
import ca.refactored.EntityClasses.Friend_Request;
import ca.refactored.EntityClasses.Request;

import static org.junit.Assert.*;

public class RequestTest {
    @Test
    public void getUsernames(){
        String user1 = "Nick";
        String user2 = "Daniela";
        Request request = new Friend_Request(user1, user2);
        assertEquals(user1, request.getsenderUID());
        assertEquals(user2, request.getrecipientUID());
    }

    @Test
    public void setUsernames(){
        String user1 = "Nick";
        String user2 = "Daniela";
        Request request = new Friend_Request("mark", "Joe");
        request.setrecipientUID(user2);
        request.setsenderUID(user1);
        assertEquals(user1, request.getsenderUID());
        assertEquals(user2, request.getrecipientUID());
    }

    @Test
    public void testRequestType(){
        String requestType = "BorrowRequest";
        Request request = new Friend_Request("mark", "Joe");
        assertEquals("Friend_Request", request.getRequestType());
        request.setRequestType(requestType);
        assertEquals(requestType, request.getRequestType());
    }

    @Test
    public void getStatus(){
        String status = "Pending";
        Request request = new Friend_Request("mark", "Joe");
        assertEquals(status, request.getStatus());
    }

    @Test
    public void setStatus(){
        String status = "Something Else";
        Request request = new Friend_Request("mark", "Joe");
        request.setStatus(status);
        assertEquals(status, request.getStatus());
    }

    @Test
    public void Accept(){
        String status = "Accepted";
        Request request = new Friend_Request("mark", "Joe");
        request.accept();
        assertEquals(status, request.getStatus());
    }

    @Test
    public void Deny(){
        String status = "Denied";
        Request request = new Friend_Request("mark", "Joe");
        request.deny();
        assertEquals(status, request.getStatus());
    }

    @Test
    public void Cancel(){
        String status = "Cancelled";
        Request request = new Friend_Request("mark", "Joe");
        request.cancel();
        assertEquals(status, request.getStatus());
    }

    @Test
    public void testRequestId(){
        String requestId = "1";
        Request request = new Friend_Request("mark", "Joe");
        request.setRequestId(requestId);
        assertEquals(requestId, request.getRequestId());
    }

    @Test
    public void testISBN(){
        String ISBN = "10-5";
        BorrowRequest request = new BorrowRequest("mark", "Joe",
                        "2", "15");
        request.setIsbn(ISBN);
        assertEquals(ISBN, request.getIsbn());
    }

    @Test
    public void testBookId(){
        String bookId = "5";
        BorrowRequest request = new BorrowRequest("mark", "Joe",
                "2", "15");
        request.setBookId(bookId);
        assertEquals(bookId, request.getBookId());
    }

}
