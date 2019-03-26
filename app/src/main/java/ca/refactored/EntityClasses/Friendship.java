/*
* Created by Delaney 03/24/2019
*
* Instances in in Add_Remove_Friend_DB,
*
* Friendship is assumed to be commutative, it does not matter who is friend1 or friend2.
*
* */


package ca.refactored.EntityClasses;

public class Friendship {
    private String friend1;
    private String friend2;

    public Friendship(String friend1, String friend2){
        this.friend1 = friend1;
        this.friend2 = friend2;
    }

    public String getFriend1() {
        return friend1;
    }

    public void setFriend1(String friend1) {
        this.friend1 = friend1;
    }

    public String getFriend2() {
        return friend2;
    }

    public void setFriend2(String friend2) {
        this.friend2 = friend2;
    }
}
