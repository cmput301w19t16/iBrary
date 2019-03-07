package ca.rededaniskal;

public class ratingPost extends Post {
    private float rating;


    public ratingPost(String title, String message,
                      String username, String ISBN, Float rating){
        super(title, message, username, ISBN, "ratingPost");
        this.rating = rating;
    }

    public float getRating() {return rating;}

    public void setRating(Float nRating){ this.rating = nRating;}
}
