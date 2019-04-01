package ca.rededaniskal.BusinessLogic;

import android.util.Log;

import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Database.MasterBookDb;
import ca.rededaniskal.Database.Search_Books_Db;
import ca.rededaniskal.Database.Search_Users_DB;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.User;


public class Search_Logic {

   Search_Fragment parent;

    String orderby;

    Search_Books_Db db;
    ArrayList<Master_Book> bookList;
    private ArrayList<User> userList;
    HashSet<String> isbns;



    public Search_Logic(Search_Fragment p, ArrayList<Integer> chosen, String search_string) {
        Log.d("Searchlog", "**************In search Logic");
        parent = p;
        bookList = new ArrayList<>();
        userList = new ArrayList<>();


        for (int i : chosen) {
            setOrderby(i);
            if(orderby.equals("friend")){
                Log.d("Searchlog", "**************Firned");
                    Search_Users_DB dbu = new Search_Users_DB(parent, search_string);
                    dbu.getUserMatches();
                }
            }


            String[] each = search_string.split("\\s+");
        Log.d("Searchlog", "*************-----> String list : " + each);
            for (String s : each) {
                if (chosen.contains(0)) {
                    new Search_Books_Db(parent, s).queryAuthorData();
                }
                if (chosen.contains(1) ) {
                    Log.d("Searchlog", "*************-----> Calling query title");
                    new Search_Books_Db(parent, s).queryTitleData();


                }
                if (chosen.contains(2)&&Character.isDigit(s.charAt(0))){
                    new Search_Books_Db(parent, s).getSingleMasterBook();


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
            orderby = "friend";
            break;
    }

}

}

