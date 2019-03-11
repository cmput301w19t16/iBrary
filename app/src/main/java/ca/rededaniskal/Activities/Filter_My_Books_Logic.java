package ca.rededaniskal.Activities;

import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book_List;

public class Filter_My_Books_Logic {
    String filter;
    Book_List book_list;

    public Filter_My_Books_Logic(String f, Book_List b) {

        this.filter =f;
        this.book_list =b;



    }

    private HashMap<String,String> parseFilter(){
        HashMap s = new HashMap();
        return s;

    }

    private Book_List newBooks (){
        Book_List b_l = new Book_List();


        return b_l;
    }


}
