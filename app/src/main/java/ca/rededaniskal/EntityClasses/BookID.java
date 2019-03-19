package ca.rededaniskal.EntityClasses;

public class BookID {

    String ID;
    private static BookID bookID;

    protected BookID(String key){
        this.ID = key;

    }

    public static BookID getInstance(String key){
        if (bookID ==null){
            bookID = new BookID(key);
        }
       return bookID;
    }





    public String getID() {
        return ID;
    }
}
