package ca.rededaniskal.BusinessLogic;
//Created by Revan on 2019-03-28

import java.util.ArrayList;

public class Establish_Exchange_Logic {

    public Establish_Exchange_Logic(){ }

    //Concatenates an array of date values so we can use it for timeStamp
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
