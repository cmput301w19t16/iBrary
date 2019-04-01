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

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Activities.Edit_Book_Instance_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class K_EditBookDetailsTest extends ActivityTestRule<Edit_Book_Instance_Activity> {

    private Solo solo;

    public K_EditBookDetailsTest() {
        super(Edit_Book_Instance_Activity.class);
    }

    @Rule
    public ActivityTestRule<Edit_Book_Instance_Activity> rule =
            new ActivityTestRule<>(Edit_Book_Instance_Activity.class, true, true);

    @Before
    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void editDetailsTest() {
        solo.assertCurrentActivity("Wrong activity", Edit_Book_Instance_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.editTitle), "Happy Potter");
        solo.enterText((EditText) solo.getView(R.id.editAuthor), "J.K. Rowling");
        solo.enterText((EditText) solo.getView(R.id.editISBN), "1234567");
        solo.clickOnButton("Save");
        // solo.assertCurrentActivity("Wrong activity", Book_Details_Activity.class);
    }

    @Test
    public void deleteBook() {
        solo.assertCurrentActivity("Wrong activity", Edit_Book_Instance_Activity.class);
        solo.clickOnButton("Remove from library");
        solo.assertCurrentActivity("Wrong activity", Book_Details_Activity.class);
    }
}
