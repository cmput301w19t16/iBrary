package ca.rededaniskal.BusinessLogic;

import java.util.ArrayList;

/**
 * Concatenates an array of date values to be used for timeStamp
 *
 * @since 2019-03-28
 * @author Revan
 */

public class Establish_Exchange_Logic {

    public Establish_Exchange_Logic(){ }

    public String dateConcat(ArrayList<Integer> vals ){
        String returnStr = "";
        int i = 0;
        int lastInd = vals.size()-1;

        for (Integer item : vals){
            returnStr.concat(Integer.toString(item));

            if (i!= lastInd){
                returnStr.concat("-");
            }

            i += 1;

        }
        return returnStr;
    }
}
