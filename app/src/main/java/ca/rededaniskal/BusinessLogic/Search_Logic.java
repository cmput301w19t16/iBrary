package ca.rededaniskal.BusinessLogic;

import android.util.Log;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Database.Search_Books_Db;
import ca.rededaniskal.EntityClasses.Master_Book;


public class Search_Logic {

   Search_Fragment parent;

    String orderby;

    Search_Books_Db db;
    ArrayList<Master_Book> bookList;
    HashSet<String> isbns;


    public Search_Logic(Search_Fragment p, ArrayList<Integer> chosen, String search_string) {
        parent = p;
        bookList = new ArrayList<>();
        isbns = new HashSet<>();

        for (int i : chosen) {
            setOrderby(i);
            db = new Search_Books_Db(parent,orderby, search_string );
            db.queryData();
            }

        String[] each = search_string.split("\\s+");
        for(String s: each){
            if (chosen.contains(0)){
               new Search_Books_Db(parent, s).queryAuthorData();
            }
            if (chosen.contains(1)){
                Log.d("Searchlog", "*************-----> Calling query title");
                new Search_Books_Db(parent, s).queryTitleData();


            }
        }
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

