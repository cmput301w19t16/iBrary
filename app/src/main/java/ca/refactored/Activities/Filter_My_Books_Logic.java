package ca.refactored.Activities;
/*author Skye*/

//Given a book_list from View_My_Library_Activity, filters books by status

//TODO: Move to correct folder





import java.util.ArrayList;
import java.util.HashMap;

import ca.refactored.EntityClasses.Book_Instance;
import ca.refactored.EntityClasses.Book_List;

/**
 * This class takes care of filtering books based on different qualifiers.
 */

public class Filter_My_Books_Logic {
    ArrayList<Integer> filter;
    Book_List book_list;

    public Filter_My_Books_Logic(ArrayList<Integer>f, Book_List b) {

        this.filter =f;
        this.book_list =b;



    }
    private ArrayList<String> parseFilter()
    {
        ArrayList<String> s=new ArrayList<>();


        HashMap<Integer, String> hashMap = new HashMap();
       hashMap.put(0, "Available");
       hashMap.put(1, "Requested");
       hashMap.put(2, "Accepted");
       hashMap.put(3, "Borrowed");
       //Create usable filter

       for (Integer k:filter){
           s.add(hashMap.get(k));
       }

       return s;
    }




    public Book_List newBooks(){


        Book_List b_l = new Book_List();
        for (Book_Instance b: book_list.getBooks()){
            String status = b.getStatus();
            if (parseFilter().contains(status)){
                b_l.addBook(b);
            }




        }

        return b_l;
    }


}
