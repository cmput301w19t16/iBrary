package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.beans.IndexedPropertyChangeEvent;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;
import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Activities.Edit_Book_Instance_Activity;
import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.Signup_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.Activities.View_Friends_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;
import ca.rededaniskal.Activities.View_Text_Post_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class EditBookDetailsTest extends ActivityTestRule<Edit_Book_Instance_Activity> {

    private Solo solo;

    public EditBookDetailsTest() {
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
