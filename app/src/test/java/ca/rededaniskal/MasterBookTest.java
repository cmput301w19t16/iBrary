package ca.rededaniskal;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MasterBookTest {

    @Test
    public void TestAddRating() {
        MasterBook book = new MasterBook("Programming", "Jack", "978-3-16-148410-0");

        book.addRating("dteodore", 5.00);
        Double rating = book.getUserRating("dteodore");
        assertEquals((Double)5.00, rating);

        book.addRating("revan", 0.00);
        Double rating1 = book.getUserRating("revan");
        assertEquals((Double)0.00, rating1);
    }

    @Test
    public void TestGetAvgRating() {
        MasterBook book = new MasterBook("Programming", "Jack", "978-3-16-148410-0");

        book.addRating("dteodore", 5.00);
        book.addRating("revan", 0.00);
        book.addRating("delaney", 2.50);

        Double avg = book.getAvgRating();

        assertEquals((Double)2.50, avg);
    }

    @Test
    public void TestGetUserRatings() {
        MasterBook book = new MasterBook("Programming", "Jack", "978-3-16-148410-0");

        book.addRating("dteodore", 5.00);
        book.addRating("revan", 0.00);

        Double rating = book.getUserRating("dteodore");
        assertEquals((Double)5.00, rating);

        rating = book.getUserRating("delaney");
        assertEquals(null, rating);
    }

    @Test
    public void TestDeleteUserRating() {
        MasterBook book = new MasterBook("Programming", "Jack", "978-3-16-148410-0");

        book.addRating("dteodore", 5.00);
        book.addRating("revan", 0.00);

        book.deleteUserRating("dteodore");
        Double avg = book.getAvgRating();
        assertEquals((Double)0.00, avg);

        book.deleteUserRating("revan");
        avg = book.getUserRating("revan");
        assertEquals(null, avg);
    }
    
}
