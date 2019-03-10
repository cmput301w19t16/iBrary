package ca.rededaniskal.Database;
//reference :  https://www.youtube.com/watch?v=zVjSnhJu9qw


import android.support.annotation.NonNull;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;



import ca.rededaniskal.EntityClasses.Book_Instance;
import ca.rededaniskal.EntityClasses.Book_List;



public class Data_Provider {
    private Integer u;
    private char readOr;
    private Book_Instance book_instance;
    private Book_List book_list;
    private HashMap<Integer, String> urls;

    public Data_Provider() {

        urls = new HashMap<>();
        urls.put(-1, "master-books");

        urls.put(2, "book-instances");
        urls.put(3,"book-instances/all-books" );
        urls.put(4, "book-status/a");
        urls.put(6, "book-status/b");
        urls.put(8, "book-status/r");
        urls.put(10, "book-status/c");
        urls.put(34, "user/user-books");
        urls.put(17, "user/user-posts");
        urls.put(19, "users");
        urls.put(23, "posts");
        urls.put(14, "requests");

        book_list = new Book_List();




    }






    public Book_List getBook_list() {
        return book_list;
    }

    public void bookQuery(Integer url) {
        //String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference bookRef= FirebaseDatabase
                .getInstance()
                .getReference()
                .child(urls.get(url));

        bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d: dataSnapshot.getChildren()){
                    Book_Instance b  =d.getValue(Book_Instance.class);
                    collect_books(b);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });


        return;



    }

    public Book_Instance getBook(Integer url, final String bookID){
        if (url%2!=0 || url%17==0) return null;
        else{
            DatabaseReference bookRef = FirebaseDatabase.getInstance().getReference().child(urls.get(url));

           bookRef.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if (dataSnapshot.hasChild(bookID)){
                       Book_Instance book = dataSnapshot.getValue(Book_Instance.class);
                       book.setBookID(bookID);
                       setBook_instance(book);


                   }
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   throw databaseError.toException();


               }
           });

        }

        return this.book_instance;


    }





    public  boolean insert(Book_Instance book_instance) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference newRef = databaseReference.child(urls.get(2));
        String id = newRef.push().getKey();
        if (!newRef.child(id).setValue(book_instance).isSuccessful()){
            return false;
        };

        for (Integer i: urls.keySet()){
            newRef = databaseReference.child(urls.get(i));
            if (i%2 ==0 && i>2) {
                if (!newRef.child(id).setValue(book_instance).isSuccessful()){
                    return  false;
                }



                if (i%17 ==0){
                    if(!newRef.child(user).child(id).setValue(book_instance).isSuccessful()){
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public Integer deleteBookInstance(Integer url, String id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        for (Integer i: urls.keySet()){
            if (i%2 ==0) {
                DatabaseReference newRef = databaseReference.child(urls.get(i));
                if (i%17 ==0){
                    newRef.child(user).child(id).removeValue();
                }
                else if (i%7 ==0){
                    //TODO
                }
                else{
                    newRef.child(id).removeValue();
                }

            }
            else{
                return null;
            }


        }

        return 1;

    }


    public boolean update(Book_Instance book_instance) {
        String id = book_instance.getBookID();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference newRef = databaseReference.child(urls.get(2));

        if (!newRef.child(id).setValue(book_instance).isSuccessful()){
            return false;
        };

        for (Integer i: urls.keySet()){
            newRef = databaseReference.child(urls.get(i));
            if (i%2 ==0 && i>2) {
                if (!newRef.child(id).setValue(book_instance).isSuccessful()){
                    return  false;
                }



                if (i%17 ==0){
                    newRef.child(user).child(id).setValue(book_instance);


                }


            }


        }






        return true;
    }




    public void setBook_instance(Book_Instance book){
        this.book_instance = book;
    }

    private void collect_books(Book_Instance b){
        this.book_list.addBook(b);

    }


}