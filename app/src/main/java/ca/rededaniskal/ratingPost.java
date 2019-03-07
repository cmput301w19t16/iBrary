package ca.rededaniskal;

public class ratingPost extends Post {
    private double rating;


    public ratingPost(String message, String username, String ISBN, double rating){
        super(message, username, ISBN, "ratingPost");
        this.rating = rating;
    }

    public double getRating() {return rating;}

    public void setRating(double nRating){ this.rating = nRating;}
}
