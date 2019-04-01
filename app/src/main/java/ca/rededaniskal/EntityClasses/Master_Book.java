package ca.rededaniskal.EntityClasses;


import android.util.Log;

import android.graphics.Bitmap;


import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book;

public class Master_Book extends Book implements Serializable {

    private Float avgRating;
    private Integer totalNumRating;
    private Float sumRatings;

    private HashMap<String, Float>mapUsersRating;
    private String googleCover;


    public  Master_Book(){}

    public Master_Book(String newTitle, String newAuthor, String newIsbn, String mcover){
        super(newTitle, newAuthor, newIsbn);
        this.googleCover = mcover;
        avgRating = -1.00f;
        totalNumRating = -1;
        mapUsersRating = new HashMap<String, Float>();
    }
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
        if (mapUsersRating!=null) {
            totalNumRating = mapUsersRating.size();
            sumRatings = 0.0f;
            for (Float d : mapUsersRating.values()) {
                sumRatings += d;
            }

            if (totalNumRating != 0) {
                avgRating = sumRatings / totalNumRating;
                return avgRating;
            }

            else{
                Log.d("null", "total ratings 0");
                return null;}
        }
        else{
            Log.d("null", "mapUserRatings null");
            return null;
        }
    }

    public Float getUserRating(String username){
        if( mapUsersRating!=null)
        {return mapUsersRating.getOrDefault(username, null);}
        return null;

    }
    public void deleteUserRating(String username){
        if (mapUsersRating!=null){
        mapUsersRating.remove(username);}

    }

    public void setMapUsersRating(HashMap<String, Float> mapUsersRating) {
        this.mapUsersRating = mapUsersRating;
    }

    public HashMap<String, Float> getMapUsersRating() {
        return mapUsersRating;
    }

    public void setGoogleCover(String newCover){
        this.googleCover = newCover;
    }

    public String getGoogleCover(){
        return this.googleCover;
    }

}
