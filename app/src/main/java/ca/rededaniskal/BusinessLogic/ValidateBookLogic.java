/* TYPE:
 * Logic
 *
 * PURPOSE:
 * Logic relevent to adding a book
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;
/*author : Skye */
//NOT IMPLEMENTED FOR PROJECT PART 4: CODE FOR VALIDATING THE BOOK INSTANCE FIELDS

//TODO: validate book id, validate against master, validate 10-digit and 13-digit isbn

/**
 * Represents the logic related to validating the fields of a book
 */

//import ca.rededaniskal.Database.AddBookDb;
import android.content.Context;
import android.util.Log;
import android.graphics.Bitmap;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.String;

import ca.rededaniskal.Database.AddBookDb;
import ca.rededaniskal.Database.EditBookDb;
import ca.rededaniskal.Database.Write_Post_DB;
import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Post;

import static com.google.android.gms.common.util.ArrayUtils.contains;

public class ValidateBookLogic {

    private String title;
    private String author;
    private String ISBN;
    private Context context;

    private String titleError;
    private String authorError;
    private String ISBNError;


    //Constructor

    public ValidateBookLogic(String title, String author, String ISBN, Context context) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.context = context;

    }
    public ValidateBookLogic(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;


    }

    public String getTitle() {
        return title;
    }



    public String getAuthor() {
        return author;
    }



    public String getISBN() {
        return ISBN;
    }



    //Validate the title of the new book, ensures
    public boolean validateTitle() {
        if (this.title.isEmpty()) {
            titleError = "Required Field";
            return false;
        }
        else {
            String[] words = this.title.split("\\s+");
            char[] let = words[0].toCharArray();
            let[0] = Character.toUpperCase(let[0]);
            words[0] = new String(let);

            for (int i = 1; i < words.length; i++) {
                String[] joins = {"the", "and", "or", "if", "a", "an", "at"};
                char[] letters = words[i].toCharArray();


                letters[0] = Character.toUpperCase(letters[0]);

                words[i] = new String(letters);


            }
            this.title = String.join(" ", words);
        }
        titleError = title;

    return true;

    }

    //Validate the author of the new book
    public boolean validateAuthor() {
        if (this.author.isEmpty()) {
            authorError = "Required Field";
            return false;
        }

        else if (!this.author.equals("bell hooks")) {

            String[] words = this.author.split("\\s+");
            Log.d("length author", String.valueOf(words.length)) ;
            for (int i = 0; i < words.length; i++) {
                char[] letters = words[i].toCharArray();
                letters[0] = Character.toUpperCase(letters[0]);
                words[i] = new String(letters);

            }
            this.author = String.join(" ", words);
        }
        authorError = author;
        return true;

    }

    //Validate the ISBN
    public boolean validateISBN() {

        int how_many=0;
        int sum=0;
        int mult=1;

       ISBN = ISBN.replaceAll("-", "");
       ISBN = ISBN.replaceAll(" ", "");
       if (ISBN.length()!=13){ISBNError = "Not a valid 13-digit ISBN";
       return false;}

            for (char digit: ISBN.toCharArray()){
                if (how_many%2==1){mult =3;}
                else{mult=1;}
                if (!Character.isDigit(digit)){

                    this.ISBNError =   "Illegal character '"+digit+"'.";
                    return false;
                }
                sum += Integer.parseInt(String.valueOf(digit))*mult;
                how_many+=1;
            }
            if (sum%10!=0){this.ISBNError = "Not a valid ISBN.";
            return false;}
            ISBNError =ISBN;
            return true;
        }





    //Returns Whether the book is valid
    public String isValid() {
        //Currently raises no errors, businessLogic is always Valid
        //TODO: implement this when testing stages are done for basic functionality
        if (! (validateTitle()&&validateAuthor()&&validateISBN())){
            return "Book Not Saved!";
        }


        return "";
    }

    /**
     * Saves the information to the database
     * @param book      Represents the book
     * @param url       Represents the url
     */

    public void saveInformation(Book_Instance book, String url){
        new AddBookDb(book, url);
        Post post = new Post("Posted a new book", book.getOwner(), ISBN, "New Book");
        Write_Post_DB db = new Write_Post_DB(post);
        db.addPostToFollowersFeed();
    }

    public void updateInformation(Book_Instance book_instance){
        EditBookDb db = new EditBookDb();
        db.EditBookData(book_instance);
    }

    public void delete(String bookId){

        EditBookDb db = new EditBookDb();
        db.DeleteBook(bookId);


    }

    public String getTitleError() {
        return titleError;
    }

    public String getAuthorError() {
        return authorError;
    }

    public String getISBNError() {
        return ISBNError;
    }
}