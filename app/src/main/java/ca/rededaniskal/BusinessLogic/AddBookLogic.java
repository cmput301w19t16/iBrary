package ca.rededaniskal.BusinessLogic;


//import ca.rededaniskal.Database.AddBookDb;
import ca.rededaniskal.EntityClasses.Book_Instance;

public class AddBookLogic {

    private String title;
    private String author;
    private String ISBN;


    public AddBookLogic(String title, String author, String ISBN) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
    }

    public String validateTitle(){

        String error ="";

        return error;
    }

    public String validateAuthor(){

        String error ="";

        return error;
    }


    public String validateISBN(){

        String error ="";


        return error;
    }

    public boolean isValid(){

        return true;
    }



    public String addBookSuccess(Book_Instance bookInstance){
        String bookID = "";

//        AddBookDb db = new AddBookDb();
//        bookID= db.addBookToDatabase(bookInstance);
//
        return bookID;



    }





}
