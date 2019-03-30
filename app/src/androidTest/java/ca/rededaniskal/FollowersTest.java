package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.View_All_Users_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class FollowersTest extends ActivityTestRule<Main_Activity> {

    private Solo solo;

    public FollowersTest() {
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
    public void testAddFollower() {
        solo.assertCurrentActivity("Wrong Activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.scrollDown();
        solo.clickOnButton("View All Users");
        solo.clickInRecyclerView(2);
        solo.clickOnButton("Follow");
//        TextView username = solo.getCurrentActivity().findViewById(R.id.title);
//        solo.goBack();
//        //solo.goBackToActivity("Main_Activity");
//        solo.goBack();
//        solo.clickOnButton("Users I'm Following");
//        assertTrue(solo.waitForText(username.toString()));
    }
}