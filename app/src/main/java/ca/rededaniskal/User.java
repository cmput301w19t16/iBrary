package ca.rededaniskal;

import android.media.Image;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class User {
    private String userName;
    private String email;
    private String phoneNumber;
    private String location;
    //private Image profilePic;
    private ArrayList<User> friends;
    private BookList ownedBooks;
    private BookList borrowedBooks;
    private BookList requestedBooks;
    private ArrayList<User> blockedUsers;

    private BookInstance favBook;

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

    public Book getFavBook() {
        return favBook;
    }

    public void setFavBook(BookInstance favBook) {
        this.favBook = favBook;
    }
    /*
    public Image getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Image profilePic) {
        this.profilePic = profilePic;
    }
    */



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
        for (int i = 0; i < ownedBooks.size(); i++){
            this.deleteOwnedBook(ownedBooks.getBookByIndex(i));
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
        this.setPhoneNumber("");
        this.setLocation("[deleted]");
        blockedUsers.clear();
    }

    //returns True if all the users books are with the user
    private boolean allBooksReturned(){
        Boolean allReturned = true;
        for (int i = 0; i < ownedBooks.size(); i++){
            if ((ownedBooks.getBookByIndex(i).getStatus() == "accepted") || (ownedBooks.getBookByIndex(i).getStatus() == "borrowed")){
                allReturned = false;
                return allReturned;
            }
        }
        return allReturned;
    }
    
}
