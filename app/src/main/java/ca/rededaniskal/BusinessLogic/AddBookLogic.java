/* TYPE:
 * Logic
 *
 * PURPOSE:
 * Logic relevent to adding a book
 *
 * ISSUES:
 */
package ca.rededaniskal.BusinessLogic;


//import ca.rededaniskal.Database.AddBookDb;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.lang.String;
import java.util.*;



import ca.rededaniskal.EntityClasses.Book_Instance;

import static com.google.android.gms.common.util.ArrayUtils.contains;

public class AddBookLogic {

    private String title;
    private String author;
    private String ISBN;


    public AddBookLogic(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }


    public String validateTitle() {
        String error = "";
        String[] words = this.title.split("/s*");
        char[] let = words[0].toCharArray();
        let[0] = Character.toUpperCase(let[0]);
        words[0] = let.toString();

        for (int i = 1; i < words.length; i++) {
            String[] joins = {"the", "and", "or", "if", "a", "an", "at"};
            char[] letters = words[i].toCharArray();
            if (!contains(joins, words[i])) {
                letters[0] = Character.toUpperCase(letters[0]);
            }
            words[i] = letters.toString();

        }
        title = words.toString();


        return error;
    }


    public String validateAuthor() {
        String error = "";

        if (!author.equals("bell hooks")) {

            String[] words = this.author.split(" ");
            for (int i = 0; i < words.length; i++) {
                char[] letters = words[i].toCharArray();
                letters[0] = Character.toUpperCase(letters[0]);
                words[i] = letters.toString();

            }
            author = words.toString();


        }

        return error;
    }


    public String validateISBN() {
        String error = "";
        int how_many=0;
        int sum;

        String[] nums = ISBN.split("-|/s");
        int even = 0;

        for (String n: nums){

            char[] num = n.toCharArray();
            how_many+=num.length;
            for (char digit: num){
                if (Character.isDigit(digit)){

                }


            }
        }

        return error;
    }


    public boolean isValid() {

        return true;
    }


}