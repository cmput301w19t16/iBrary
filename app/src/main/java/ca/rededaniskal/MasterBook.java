package ca.rededaniskal;

import java.util.HashMap;
import java.util.Map;

public class MasterBook extends Book {

    private Double avgRating;
    private Integer totalNumRating;
    private Double sumRatings;
    private Map<String, Double>mapUsersRating;

    public MasterBook(String newTitle, String newAuthor, String newIsbn){
        super(newTitle, newAuthor, newIsbn);
        avgRating = -1.00;
        totalNumRating = -1;
    }

    public void addRating(String username, Double rating){
        mapUsersRating.put(username, rating);
    }

    public Double getAvgRating() {
        totalNumRating = mapUsersRating.size();
        sumRatings = 0.00;
        for (Double d : mapUsersRating.values()) {
            sumRatings += d;
        }

        if( avgRating != -1.00 && totalNumRating != 0){
            avgRating = sumRatings/totalNumRating;
            return avgRating;
        }
        else{
            return null;
        }
    }

    public Double getUserRating(String username){
        return mapUsersRating.getOrDefault(username, null);
    }
    public void deleteUserRating(String username){
        mapUsersRating.remove(username);
    }

}
