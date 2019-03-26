package ca.refactored.EntityClasses;

import java.io.Serializable;
import java.util.HashMap;

public class Master_Book extends Book implements Serializable {

    private Float avgRating;
    private Integer totalNumRating;
    private Float sumRatings;
    private HashMap<String, Float>mapUsersRating;

    public Master_Book(String newTitle, String newAuthor, String newIsbn){
        super(newTitle, newAuthor, newIsbn);
        avgRating = -1.00f;
        totalNumRating = -1;
        mapUsersRating = new HashMap<String, Float>();
    }

    public void addRating(String username, Float rating){
        mapUsersRating.put(username, rating);
    }

    public Float getAvgRating() {
        totalNumRating = mapUsersRating.size();
        sumRatings = 0.0f;
        for (Float d : mapUsersRating.values()) {
            sumRatings += d;
        }

        if(totalNumRating != 0){
            avgRating = sumRatings/totalNumRating;
            return avgRating;
        }
        else{
            return null;
        }
    }

    public Float getUserRating(String username){
        return mapUsersRating.getOrDefault(username, null);
    }
    public void deleteUserRating(String username){
        mapUsersRating.remove(username);
    }

}
