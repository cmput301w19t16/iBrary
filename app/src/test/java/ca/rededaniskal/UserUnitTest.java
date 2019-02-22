package ca.rededaniskal;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public void getFavBook() {

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
        user.setUserName(email2);
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

    }

    @Test
    private void allBooksReturned() {
    }
}