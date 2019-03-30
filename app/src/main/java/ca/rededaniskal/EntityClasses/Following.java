/*
* Created by Delaney 03/24/2019
*
* Instances in in Follow_DB,
*
* Following is assumed to be commutative, it does not matter who is leader or follower.
*
* */


package ca.rededaniskal.EntityClasses;

public class Following {
    private String leader;
    private String follower;

    public Following(String follower, String leader){
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
