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

/**
 * This is the logic that determines which books or users will be displayed in the
 * recycler view
 */

public class Search_Logic {

   Search_Fragment parent;

    String orderby;

    Search_Books_Db db;
    ArrayList<Master_Book> bookList;
    private ArrayList<User> userList;
    HashSet<String> isbns;

    /**
     * Determines whether or not to get list from books or users, and if the list is of books,
     * queries them based on author, title or ISBN
     * @param p
     * @param chosen
     * @param search_string
     */

    public Search_Logic(Search_Fragment p, ArrayList<Integer> chosen, String search_string) {
        Log.d("Searchlog", "**************In search Logic");
        parent = p;
        bookList = new ArrayList<>();
        userList = new ArrayList<>();



        for (int i : chosen) {

            if(i==3){
                Log.d("Searchlog", "**************Firned");
                    Search_Users_DB dbu = new Search_Users_DB(parent, search_string);
                    dbu.getUserMatches();
                }
            }


        String[] each = search_string.split("\\s+");
        for(String s1: each){
            String s=s1.toLowerCase();

            if (chosen.contains(0)||chosen.isEmpty()){
               new Search_Books_Db(parent, s).queryAuthorData();
            }
            if (chosen.contains(1)||chosen.isEmpty()){
                Log.d("Searchlog", "*************-----> Calling query title");
                new Search_Books_Db(parent, s).queryTitleData();
            }
       
          
              if (chosen.contains(2)&&Character.isDigit(s.charAt(0))){
                    new Search_Books_Db(parent, s).getSingleMasterBook();

            }
        }
    }


}

