package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ThrowOnExtraProperties;
import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;
import ca.rededaniskal.Activities.Edit_Book_Instance_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;

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
    public void addFakeBookToLibrary() {
        solo.assertCurrentActivity("Wrong activity", Add_Book_To_Library_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.addTitle), "Hairy Poter");
        solo.enterText((EditText) solo.getView(R.id.addAuthor), "J.K. Rolling");
        solo.enterText((EditText) solo.getView(R.id.addISBN), "12345677");
        solo.clickOnButton("Add Book To Library");
        solo.assertCurrentActivity("Wrong activity", Add_Book_To_Library_Activity.class);
    }

    @Test
    public void addRealBookToLibrary() {
        solo.assertCurrentActivity("Wrong activity", Add_Book_To_Library_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.addTitle), "The Hunger Games");
        solo.enterText((EditText) solo.getView(R.id.addAuthor), "Suzanne Collins");
        solo.enterText((EditText) solo.getView(R.id.addISBN), "9781470106324");
        solo.clickOnButton("Add Book To Library");
        solo.assertCurrentActivity("Wrong activity", Add_Book_To_Library_Activity.class);
        assertTrue(solo.waitForText("Hunger"));
    }

    @Test
    public void deleteBookFromLibrary() {
        solo.assertCurrentActivity("Wrong activity", Add_Book_To_Library_Activity.class);
        solo.clickOnText("Introduction");
        solo.clickOnButton("Edit Book");
        solo.assertCurrentActivity("Wrong activity", Edit_Book_Instance_Activity.class);
        View deleteBtn = solo.getView(R.id.delete);
        solo.clickOnView(deleteBtn);
        solo.assertCurrentActivity("Wrong activity", View_My_Library_Activity.class);
    }
}
