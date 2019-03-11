package ca.rededaniskal.Activities;

import java.util.ArrayList;
import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;

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
