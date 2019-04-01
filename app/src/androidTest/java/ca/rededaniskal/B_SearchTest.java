package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;
import ca.rededaniskal.Activities.Main_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class B_SearchTest extends ActivityTestRule<Main_Activity> {

    private Solo solo;

    public B_SearchTest() {
        super(Main_Activity.class);
    }

    @Rule
    public ActivityTestRule<Main_Activity> rule =
            new ActivityTestRule<>(Main_Activity.class, true, true);

    @Before
    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void testSearch() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Search");
        solo.clickOnButton("Search By");
        solo.clickOnText("Title");
        solo.clickOnText("OK");
        View searchBtn = solo.getView(R.id.fragmentSearchView);
        solo.clickOnView(searchBtn);

    }
}
