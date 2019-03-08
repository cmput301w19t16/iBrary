package ca.rededaniskal;

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



    public String addBookSuccess(BookInstance bookInstance){
        String bookID;

        AddBookDb db = new AddBookDb();
        bookID= db.addBookToDatabase(bookInstance);

        return bookID;



    }




}
