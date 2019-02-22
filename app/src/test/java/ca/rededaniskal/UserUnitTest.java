package ca.rededaniskal;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserUnitTest {

    @Test
    public Book getFavBook() {
        String name  = "John Doe";
        String email = "john@gmail.com";
        String phoneNumber = "780-675-8796";
        User user = new User(name, email, phoneNumber);

        Book boom = new BookInstance("Happy Potter","J.K. Rowling" , "785-3-16-275029-0", "Very Good Read" );


        return favBook;
    }

    public void setFavBook(Book favBook) {
        this.favBook = favBook;
    }




    public ArrayList<Book> getRequestedBooks() {
        return requestedBooks;
    }

    public void setRequestedBooks(ArrayList<Book> requestedBooks) {
        this.requestedBooks = requestedBooks;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        // TODO: check if userName is unique
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<User> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Book> getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(ArrayList<Book> ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(ArrayList<Book> borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    //Functionality methods
    public void addRequestedBook(Book newBook){
        requestedBooks.add(newBook);
    }

    public void deleteRequestedBook(Book deleteBook){
        requestedBooks.remove(deleteBook);
    }

    public void addOwnedBook(Book newBook){
        ownedBooks.add(newBook);
    }

    public void deleteOwnedBook(Book deleteBook){
        ownedBooks.remove(deleteBook);
    }

    public void removeAllOwnedBooks(){
        for (int i = 0; i < ownedBooks.size(); i++){
            this.deleteOwnedBook(ownedBooks.get(i));
        }
    }

    public void addFriend(User newFriend){
        friends.add(newFriend);
    }

    public void removeFriend(User removedFriend){
        friends.remove(removedFriend);
    }

    public void removeAllFriends(){
        for (int i = 0; i < friends.size(); i++){
            friends.get(i).removeFriend(this);
        }
        friends.clear();
    }

    public void addBlockedUser(User blockedUser){
        removeFriend(blockedUser);
        blockedUsers.add(blockedUser);
    }

    public void removeBlockedUser(User blockedUser){
        blockedUsers.remove(blockedUser);
    }

    public void deleteProfile(){
        assert(borrowedBooks.size() == 0);
        assert (this.allBooksReturned() == true);

        //TODO: Add remove from blocked list?
        removeAllOwnedBooks();
        removeAllFriends();
        this.setUserName("[deleted]");
        this.setEmail("[deleted]");
        this.setPhoneNumber(-1);
        this.setLocation("[deleted]");
        blockedUsers.clear();
    }

    //returns True if all the users books are with the user
    private boolean allBooksReturned(){
        Boolean allReturned = true;
        for (int i = 0; i < ownedBooks.size(); i++){
            if ((ownedBooks.get(i).getStatus() == "accepted") || (ownedBooks.get(i).getStatus() == "borrowed")){
                allReturned = false;
                return allReturned;
            }
        }
        return allReturned;
    }

}


}
