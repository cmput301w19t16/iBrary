package ca.rededaniskal.EntityClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String userName;
    private String email;
    private String phoneNumber;
    private String location;
    private String profilePic;
    private String UID;
    private ArrayList<User> friends;
    private Book_List ownedBooks;
    private Book_List borrowedBooks; // Includes accepted
    private Book_List requestedBooks;
    private ArrayList<User> blockedUsers;
    private int followerCount;
    private String urlProfilePic;

    private Master_Book favBook;

    //Constructors

    public User(){}

    public User(String userName, String email, String location){
        // TODO: check if userName is unique

        this.userName = userName;
        this.email = email;
        this.location = location;
        this.phoneNumber = "Not Provided";

        this.friends = new ArrayList<User>();
        this.ownedBooks = new Book_List();
        this.borrowedBooks  = new Book_List();
        this.requestedBooks = new Book_List();
        this.blockedUsers = new ArrayList<User>();


    }

    public User(String userName, String email, String phoneNumber, String location ){
        // TODO: check if userName is unique
        this(userName, email, location);
        this.phoneNumber = phoneNumber;
    }

    //Getters + setters

    public Master_Book getFavBook() {
        return favBook;
    }

    public void setFavBook(Master_Book favBook) {
        this.favBook = favBook;
    }

    // TODO Figure out how to instantiate a picture and store it.
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }


    public Book_List getRequestedBooks() {
        return requestedBooks;
    }

    public void setRequestedBooks(Book_List requestedBooks) {
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


    public String getPhoneNumber() {
        // TODO Check if real phone number (long?)
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public Book_List getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(Book_List ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public Book_List getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Book_List borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    //Functionality methods
    public void addRequestedBook(Book_Instance newBook){
        requestedBooks.addBook(newBook);
    }

    public void deleteRequestedBook(Book_Instance deleteBook){
        requestedBooks.removeBook(deleteBook);
    }



    public void addOwnedBook(Book_Instance newBook){

        ownedBooks.addBook(newBook);
    }

    public void deleteOwnedBook(Book_Instance deleteBook){
        ownedBooks.removeBook(deleteBook);
    }

    public void removeAllOwnedBooks(){
        ownedBooks.clear();
    }

    public void addBorrowedBook(Book_Instance newBook){
        borrowedBooks.addBook(newBook);
    }

    public void deleteBorrowedBook(Book_Instance deleteBook){
        borrowedBooks.removeBook(deleteBook);
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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
        this.setPhoneNumber("");
        this.setLocation("[deleted]");
        blockedUsers.clear();
    }

    //returns True if all the users books are with the user
    public boolean allBooksReturned(){
        boolean allReturned = true;
        for (int i = 0; i < ownedBooks.size(); i++){
            if ((ownedBooks.getBookByIndex(i).getStatus().equals("accepted")) || (ownedBooks.getBookByIndex(i).getStatus().equals("borrowed"))){
                allReturned = false;
                return allReturned;
            }
        }
        return allReturned;
    }


    //Takes in another user, and returns the number of friends in common
    public Integer numberMutualFriends(User user2){
        Integer numMutualFriends = 0;

        for (int i = 0; i< this.getFriends().size(); i++){
            if (user2.getFriends().contains(this.getFriends().get(i))){ // If user is in other uses friends list
                numMutualFriends +=1;
            }
        }

        return numMutualFriends;
    }


    //Returns True is the two users are friends, false o/w
    public Boolean isFriendsWith(User user2){
        if (user2.getFriends().contains(user2)){
            return true;
        }
        return false;
    }

}
