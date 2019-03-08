package ca.rededaniskal.EntityClasses;

import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book;

public class MasterBook extends Book {

    private Double avgRating;
    private Integer totalNumRating;
    private Double sumRatings;
    private HashMap<String, Double>mapUsersRating;

    public MasterBook(String newTitle, String newAuthor, String newIsbn){
        super(newTitle, newAuthor, newIsbn);
        avgRating = -1.00;
        totalNumRating = -1;
        mapUsersRating = new HashMap<String, Double>();
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

        if(totalNumRating != 0){
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
