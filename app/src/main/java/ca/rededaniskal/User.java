package ca.rededaniskal;

import android.media.Image;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String userName;
    private String email;
    private Integer phoneNumber;
    private String location;
    //private Image profilePic;
    private ArrayList<User> friends;
    private ArrayList<Book> ownedBooks;
    private ArrayList<Book> borrowedBooks;
    private ArrayList<User> blockedUsers;
    private ArrayList<Book> requestedBooks;



    //Constructors
    public User(String userName, String email, String location){
        // TODO: check if userName is unique

        this.userName = userName;
        this.email = email;
        this.location = location;

        this.friends = new ArrayList<User>();
        this.ownedBooks = new ArrayList<Book>();
        this.borrowedBooks  = new ArrayList<Book>();
        this.blockedUsers = new ArrayList<User>();
        this.requestedBooks = new ArrayList<Book>();
    }

    public User(String userName, String email, Integer phoneNumber, String location ){
        // TODO: check if userName is unique
        this(userName, email, location);
        this.phoneNumber = phoneNumber;
    }

    //Getters + setters
    /*
    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }
    */

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
