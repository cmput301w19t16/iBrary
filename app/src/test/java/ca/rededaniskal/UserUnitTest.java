package ca.rededaniskal;

import java.util.ArrayList;
import org.junit.Test;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.User;

import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public void getFavBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Master_Book book = new Master_Book("Happy Potter", "J.K.", "1234567890");

        user.setFavBook(book);

        assertEquals(book, user.getFavBook());
    }

    @Test
    public void setFavBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Master_Book book = new Master_Book("Happy Potter", "J.K.", "1234567890");

        user.setFavBook(book);

        assertEquals(book, user.getFavBook());
    }

    @Test
    public void getRequestedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");

        Book_List list = new Book_List();
        list.addBook(book);

        user.setRequestedBooks(list);

        assertEquals(list, user.getRequestedBooks());
    }

    @Test
    public void setRequestedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");

        Book_List list = new Book_List();
        list.addBook(book);

        user.setRequestedBooks(list);

        assertEquals(list, user.getRequestedBooks());
    }

    @Test
    public void getUserName() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        assertEquals(user.getUserName(), name);
    }

    @Test
    public void setUserName() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        user.setUserName(name2);

        assertEquals(user.getUserName(),name2 );
    }

    @Test
    public void getEmail() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        assertEquals(user.getEmail(), email);
    }

    @Test
    public void setEmail() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String email2 = "jane@gmail.com";
        user.setEmail(email2);
        assertEquals(user.getEmail(), email2);
    }

    @Test
    public void getPhoneNumber() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        assertEquals(user.getPhoneNumber(), phoneNumber);
    }

    @Test
    public void setPhoneNumber() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String phone2 = "780-000-0000";
        user.setPhoneNumber(phone2);
        assertEquals(phone2, user.getPhoneNumber());
    }

    @Test
    public void getLocation() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        assertEquals(location, user.getLocation());

    }

    @Test
    public void setLocation() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String location2 = "Red Deer, AB";
        user.setLocation(location2);

        assertEquals( location2, user.getLocation());
    }

    @Test
    public void getFriends() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addFriend(user2);
        user.addFriend(user3);

        ArrayList<User> Friends = new ArrayList<>();
        Friends.add(user2);
        Friends.add(user3);

        assertEquals(Friends, user.getFriends());

    }

    @Test
    public void setFriends() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        ArrayList<User> Friends = new ArrayList<>();
        Friends.add(user2);
        Friends.add(user3);

        user.setFriends(Friends);
        assertEquals(Friends, user.getFriends());

    }

    @Test
    public void getOwnedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "D", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter2", "J.K.", "1234567890", "D", "D", "Very Nice", "accepted");

        user.addOwnedBook(book);
        user.addOwnedBook(book2);

        Book_List list = new Book_List();

        user.setOwnedBooks(list);

        assertEquals(list, user.getOwnedBooks());
    }

    @Test
    public void setOwnedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter2", "J.K.", "1234567890", "D", "D", "Very Nice", "accepted");

        Book_List list = new Book_List();
        list.addBook(book);
        list.addBook(book2);

        user.setOwnedBooks(list);

        assertEquals(list, user.getOwnedBooks());

    }

    @Test
    public void getBorrowedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter2", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");

        Book_List list = new Book_List();
        list.addBook(book);
        list.addBook(book2);

        user.setBorrowedBooks(list);

        assertEquals(list, user.getBorrowedBooks());

    }

    @Test
    public void setBorrowedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "D", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter2", "J.K.", "1234567890", "D", "D", "Very Nice", "accepted");

        Book_List list = new Book_List();
        list.addBook(book);
        list.addBook(book2);

        user.setBorrowedBooks(list);

        assertEquals(list, user.getBorrowedBooks());

    }


    @Test
    public void getBlockedUsers() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addBlockedUser(user2);
        user.addBlockedUser(user3);

        ArrayList<User> Blocked = new ArrayList<>();
        Blocked.add(user2);
        Blocked.add(user3);

        assertEquals(Blocked, user.getBlockedUsers());
    }
    @Test
    public void setBlockedUsers() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        ArrayList<User> Blocked = new ArrayList<>();
        Blocked.add(user2);
        Blocked.add(user3);

        user.setBlockedUsers(Blocked);

        assertEquals(Blocked, user.getBlockedUsers());

    }

    //Functionality methods
    @Test
    public void addRequestedBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");

        user.addRequestedBook(book);

        assertEquals(book, user.getRequestedBooks().getBookByIndex(0));
    }

    @Test
    public void deleteRequestedBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");

        user.addRequestedBook(book);

        assertEquals(book, user.getRequestedBooks().getBookByIndex(0));

        user.deleteRequestedBook(book);

        Integer integer = 0;

        assertEquals(integer, user.getRequestedBooks().size());



    }

    @Test
    public void addOwnedBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "accepted");

        user.addOwnedBook(book);

        assertEquals(book, user.getOwnedBooks().getBookByIndex(0));

    }

    @Test
    public void deleteOwnedBook() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "D", "d", "Very Nice", "accepted");

        user.addOwnedBook(book);

        assertEquals(book, user.getOwnedBooks().getBookByIndex(0));

        user.deleteOwnedBook(book);

        Integer integer = 0;

        assertEquals(integer, user.getOwnedBooks().size());
    }

    @Test
    public void removeAllOwnedBooks() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "D", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "accepted");

        user.addOwnedBook(book);
        user.addOwnedBook(book2);

        user.removeAllOwnedBooks();


        assertEquals((Integer)0, user.getOwnedBooks().size());
    }

    @Test
    public void addFriend() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        user.addFriend(user2);

        ArrayList<User> Friends = new ArrayList<>();
        Friends.add(user2);

        assertEquals(Friends, user.getFriends());
    }

    @Test
    public void removeFriend() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        user.addFriend(user2);

        ArrayList<User> Friends = new ArrayList<>();
        Friends.add(user2);

        assertEquals(Friends, user.getFriends());
        user.removeFriend(user2);

        assertEquals(0, user.getFriends().size());

    }

    @Test
    public void removeAllFriends() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-000-0000";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addFriend(user2);
        user.addFriend(user3);

        user.removeAllFriends();

        assertEquals(0, user.getFriends().size());
    }

    @Test
    public void addBlockedUser() {

        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addBlockedUser(user3);

        assertEquals(true, user.getBlockedUsers().contains(user3));

    }

    @Test
    public void removeBlockedUser() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name3 = "Foo Bar";
        String email3 = "jane@gmail.com";
        String phoneNumber3 = "780-111-1111";
        String location3  = "Edmonton, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addBlockedUser(user3);

        assertTrue(user.getBlockedUsers().contains(user3));

        user.removeBlockedUser(user3);

        assertEquals(0, user.getBlockedUsers().size());

    }

    @Test
    public void deleteProfile() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "accepted");
        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "accepted");

        user.deleteProfile();


        assertEquals((Integer)0, user.getOwnedBooks().size());
        assertEquals(0, user.getFriends().size());
        assertEquals("[deleted]", user.getUserName());
        assertEquals("[deleted]", user.getEmail());
        assertEquals("", user.getPhoneNumber());
        assertEquals("[deleted]", user.getLocation());
        assertEquals(0, user.getBlockedUsers().size());
    }

    @Test
    public void allBooksReturned() {
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "e", "Very Nice", "available");
        Book_Instance book2 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "e", "Very Nice", "accepted");
        Book_Instance book3 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "borrowed");
        Book_Instance book4 = new Book_Instance("Happy Potter", "J.K.", "1234567890", "d", "d", "Very Nice", "requested");

        user.addOwnedBook(book);
        user.addOwnedBook(book2);
        user.addOwnedBook(book3);
        user.addOwnedBook(book4);

        assertFalse(user.allBooksReturned());

        user.removeAllOwnedBooks();

        user.addOwnedBook(book);
        user.addOwnedBook(book4);

        assertEquals((Integer)2, user.getOwnedBooks().size());

        assertTrue(user.allBooksReturned());


    }

    @Test
    public void addBorrowedBook(){
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "dlothian", "daniela", "Very Nice", "accepted");

        user.addBorrowedBook(book);

        assertEquals(book, user.getBorrowedBooks().getBookByIndex(0));



    }

    @Test
    public void deleteBorrowedBook(){
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        Book_Instance book = new Book_Instance("Happy Potter", "J.K.", "1234567890", "dlothian", "daniela", "Very Nice", "accepted");

        user.addBorrowedBook(book);

        assertEquals(book, user.getBorrowedBooks().getBookByIndex(0));

        user.deleteBorrowedBook(book);

        assertEquals((Integer)0,user.getBorrowedBooks().size());
    }

    @Test
    public void numberMutualFriends(){
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-680-8796";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Regan McKing";
        String email3 = "regan@gmail.com";
        String phoneNumber3 = "780-000-9999";
        String location3  = "Red Deer, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addFriend(user3);
        user2.addFriend(user3);
        assertEquals((Integer)1, user.numberMutualFriends(user2));
    }

    @Test
    public void isFriendsWith(){
        String name = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        String location  = "Edmonton, AB";
        User user = new User(name, email, phoneNumber, location);

        String name2 = "Jane Doe";
        String email2 = "jane@gmail.com";
        String phoneNumber2 = "780-680-8796";
        String location2  = "Edmonton, AB";
        User user2 = new User(name2, email2, phoneNumber2, location2);

        String name3 = "Regan McKing";
        String email3 = "regan@gmail.com";
        String phoneNumber3 = "780-000-9999";
        String location3  = "Red Deer, AB";
        User user3 = new User(name3, email3, phoneNumber3, location3);

        user.addFriend(user2);

        assertTrue(user.isFriendsWith(user2));
        assertFalse(user.isFriendsWith(user3));
    }
}