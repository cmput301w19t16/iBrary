package ca.rededaniskal.BusinessLogic;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Database.Search_Books_Db;
import ca.rededaniskal.EntityClasses.Master_Book;


public class Search_Logic {

   Search_Fragment parent;
    String[] equalArray;
    String orderby;
    Set<Master_Book> set;

    Search_Books_Db db;
    ArrayList<Master_Book> bookList;




    public Search_Logic(Search_Fragment p, ArrayList<Integer> chosen, String search_string) {
        parent = p;
        bookList = new ArrayList<>();
        set = new LinkedHashSet<>();

        equalArray = search_string.split("[\\p{Punct}\\s]+");
        for (String s : equalArray) {
            for (int i : chosen) {
                setOrderby(i);
                db = new Search_Books_Db(parent, orderby, s);
                set.addAll(db.getSearchlist());
            }
        }
        bookList.addAll(set);
        parent.update_books(bookList);
    }

public void setOrderby(int i){

    switch (i) {
        case 0:
            orderby = "author";
            break;
        case 1:
            orderby = "title";
            break;
        case 2:
            orderby = "isbn";
            break;
        case 3:
            orderby = "owner";
            break;


    }

}
}
