package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class AddBookToLibraryTest extends ActivityTestRule<Add_Book_To_Library_Activity> {

    private Solo solo;

    public AddBookToLibraryTest() {
        super(Add_Book_To_Library_Activity.class);
    }

    @Rule
    public ActivityTestRule<Add_Book_To_Library_Activity> rule =
            new ActivityTestRule<>(Add_Book_To_Library_Activity.class, true, true);

    @Before
    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void addBookToLibrary() {
        solo.assertCurrentActivity("Wrong activity", Add_Book_To_Library_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.addTitle), "Hairy Poter");
        solo.enterText((EditText) solo.getView(R.id.addAuthor), "J.K. Rolling");
        solo.enterText((EditText) solo.getView(R.id.addISBN), "12345677");
        solo.enterText((EditText) solo.getView(R.id.editDescription), "The latest in the " +
                "Hairy Poter series by Just Kidding Rolling");
        solo.clickOnButton("Add Book To Library");

        //solo.assertCurrentActivity("Wrong activity", View_My_Library_Activity.class);
        //assertEquals("Book was not added", solo.searchText("Hairy Poter"), true);

    }
}
