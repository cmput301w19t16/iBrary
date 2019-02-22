package ca.rededaniskal;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public Book getFavBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        User user = new User(name, email, phoneNumber);

        BookInstance bookInstance = new BookInstance("Happy Potter", "J.K. Rowling", "785-3-16-275029-0", "Very Good Read");


        return favBook;
    }

    @Test
    public void setFavBook() {

    }

    @Test
    public void getRequestedBooks() {

    }

    @Test
    public void setRequestedBooks() {

    }

    @Test
    public void getUserName() {

    }

    @Test
    public void setUserName() {

    }

    @Test
    public void getEmail() {

    }

    @Test
    public void setEmail() {

    }

    @Test
    public void getPhoneNumber() {

    }

    @Test
    public void setPhoneNumber() {

    }

    @Test
    public void getLocation() {

    }

    @Test
    public void setLocation() {

    }

    @Test
    public void getFriends() {

    }

    @Test
    public void setFriends() {

    }

    @Test
    public void getOwnedBooks() {

    }

    @Test
    public void setOwnedBooks() {

    }

    @Test
    public void getBorrowedBooks() {

    }

    @Test
    public void setBorrowedBooks() {

    }


    @Test
    public void getBlockedUsers() {

    }
    @Test
    public void setBlockedUsers() {

    }

    //Functionality methods
    @Test
    public void addRequestedBook() {

    }

    @Test
    public void deleteRequestedBook() {

    }

    @Test
    public void addOwnedBook() {

    }

    @Test
    public void deleteOwnedBook() {

    }

    @Test
    public void removeAllOwnedBooks() {

    }

    @Test
    public void addFriend() {

    }

    @Test
    public void removeFriend() {

    }

    @Test
    public void removeAllFriends() {

    }

    @Test
    public void addBlockedUser() {

    }

    @Test
    public void removeBlockedUser(User blockedUser) {

    }

    @Test
    public void deleteProfile() {

    }

    @Test
    private void allBooksReturned() {
    }
}