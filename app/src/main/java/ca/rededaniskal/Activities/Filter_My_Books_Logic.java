package ca.rededaniskal.Activities;
/*author Skye*/

//Given a book_list from View_My_Library_Activity, filters books by status

//TODO: Move to correct folder





import java.util.ArrayList;
import java.util.HashMap;

import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Display_Username;

/**
 * This class takes care of filtering books based on different qualifiers.
 */

public class Filter_My_Books_Logic {
    ArrayList<Integer> filter;
    ArrayList<Display_Username> book_list;

    public Filter_My_Books_Logic(ArrayList<Integer>f, ArrayList<Display_Username> b) {

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




    public ArrayList<Display_Username> newBooks(){


        ArrayList<Display_Username> b_l = new ArrayList<Display_Username>();
        for(int i = 0; i < book_list.size(); i++){
            Display_Username du = book_list.get(i);
            Book_Instance b = du.getBook();
            String status = b.getStatus();
            if (parseFilter().contains(status)){
                b_l.add(du);
            }




        }

        return b_l;
    }


}
