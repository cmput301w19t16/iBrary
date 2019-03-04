package ca.rededaniskal;

import android.media.Image;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String userName;
    private String email;
    private String phoneNumber;
    private String location;
    private String profilePic;
    private ArrayList<User> friends;
    private BookList ownedBooks;
    private BookList borrowedBooks; // Includes accepted
    private BookList requestedBooks;
    private ArrayList<User> blockedUsers;

    private MasterBook favBook;

    //Constructors
    public User(String userName, String email, String location){
        // TODO: check if userName is unique

        this.userName = userName;
        this.email = email;
        this.location = location;

        this.friends = new ArrayList<User>();
        this.ownedBooks = new BookList();
        this.borrowedBooks  = new BookList();
        this.requestedBooks = new BookList();
        this.blockedUsers = new ArrayList<User>();

    }

    public User(String userName, String email, String phoneNumber, String location ){
        // TODO: check if userName is unique
        this(userName, email, location);
        this.phoneNumber = phoneNumber;
    }

    //Getters + setters

    public MasterBook getFavBook() {
        return favBook;
    }

    public void setFavBook(MasterBook favBook) {
        this.favBook = favBook;
    }

    // TODO Figure out how to instantiate a picture and store it.
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }



    public BookList getRequestedBooks() {
        return requestedBooks;
    }

    public void setRequestedBooks(BookList requestedBooks) {
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

    public BookList getOwnedBooks() {
        return ownedBooks;
    }

    public void setOwnedBooks(BookList ownedBooks) {
        this.ownedBooks = ownedBooks;
    }

    public BookList getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(BookList borrowedBooks) {
        this.borrowedBooks = borrowedBooks;
    }

    public ArrayList<User> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    //Functionality methods
    public void addRequestedBook(BookInstance newBook){
        requestedBooks.addBook(newBook);
    }

    public void deleteRequestedBook(BookInstance deleteBook){
        requestedBooks.removeBook(deleteBook);
    }

    public void addOwnedBook(BookInstance newBook){

        ownedBooks.addBook(newBook);
    }

    public void deleteOwnedBook(BookInstance deleteBook){
        ownedBooks.removeBook(deleteBook);
    }

    public void removeAllOwnedBooks(){
        ownedBooks.clear();
    }

    public void addBorrowedBook(BookInstance newBook){
        borrowedBooks.addBook(newBook);
    }

    public void deleteBorrowedBook(BookInstance deleteBook){
        borrowedBooks.removeBook(deleteBook);
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
    
}
