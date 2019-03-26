package ca.rededaniskal.BusinessLogic;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Database.Search_Books_Db;
import ca.rededaniskal.EntityClasses.Master_Book;


public class Search_Logic {

   Search_Fragment parent;
    String[] equalArray;
    String orderby;
    String equalto;
    Search_Filter filter;
    Search_Books_Db db;



    public Search_Logic(Search_Fragment p, ArrayList<Integer> chosen, String search_string) {
        parent = p;
        this.equalArray = search_string.split(",");
        for (int i: chosen){
            switch (i){
                case 0:
                    filter = Search_Filter.SEARCH_BY_AUTHOR;
                    break;

                case 1:
                    filter = Search_Filter.SEARCH_BY_TITLE;
                    break;
                case 2:
                    filter = Search_Filter.SEARCH_BY_ISBN;
                    break;
                case 3:
                    filter = Search_Filter.SEARCH_BY_FRIENDS;
                    break;
            }
            get_results_for(filter, i);

        }
    }

    public void get_results_for(Search_Filter filter, int i){
        equalto = equalArray[0 ];
        switch (filter){
            case SEARCH_BY_AUTHOR:
                orderby = "author";
                break;
            case SEARCH_BY_TITLE:
                orderby= "title";
                break;
            case SEARCH_BY_ISBN:
                orderby = "isbn";
                break;
            case SEARCH_BY_FRIENDS:
                orderby = "owner";
                break;

        }
        db = new Search_Books_Db(parent, orderby, equalto );


    }

}
