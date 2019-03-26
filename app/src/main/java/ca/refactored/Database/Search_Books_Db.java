package ca.refactored.Database;

import ca.refactored.BusinessLogic.Search_Logic;

public class Search_Books_Db {
    Search_Logic parent;
    String Order;
    String Equal;
    MasterBookDb masterBookDb;
    BookInstanceDb bookInstanceDb;


    public Search_Books_Db(Search_Logic p, String filter, String is) {
        parent = p;
        Order = filter;
        Equal = is;

    }


    public void contruct_query(){


    }
}

