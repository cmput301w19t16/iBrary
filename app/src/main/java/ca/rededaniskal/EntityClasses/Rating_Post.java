package ca.rededaniskal.EntityClasses;

import ca.rededaniskal.EntityClasses.Post;

/**
 * Represents a type of post which involved a rating
 */
public class Rating_Post extends Post {
    private double rating;


    public Rating_Post(String message, String username, String ISBN, double rating){
        super(message, username, ISBN, "Rating_Post");
        this.rating = rating;
    }

    public double getRating() {return rating;}

    public void setRating(double nRating){ this.rating = nRating;}
}
