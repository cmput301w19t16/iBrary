package ca.rededaniskal;

import java.util.HashMap;
import java.util.Map;

public class MasterBook extends Book {

    private Float rating;
    private Map<String, Float>mapUsersRating;

    public MasterBook(String newTitle, String newAuthor, String newIsbn,Float newRating, String userGivingRating, Float userRating){
        super(newTitle, newAuthor, newIsbn);
        rating = newRating;
        mapUsersRating.put(userGivingRating,userRating);
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Map<String, String> getMapUsersRating() {
        return mapUsersRating;
    }

    public void setMapUsersRating(Map<String, String> mapUsersRating) {
        this.mapUsersRating = mapUsersRating;
    }
}
