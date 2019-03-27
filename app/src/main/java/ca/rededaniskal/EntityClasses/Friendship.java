/*
* Created by Delaney 03/24/2019
*
* Instances in in Write_Friendship_DB,
*
* Friendship is assumed to be commutative, it does not matter who is leader or follower.
*
* */


package ca.rededaniskal.EntityClasses;

public class Friendship {
    private String leader;
    private String follower;

    public Friendship(String leader, String follower){
        this.leader = leader;
        this.follower = follower;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }
}
