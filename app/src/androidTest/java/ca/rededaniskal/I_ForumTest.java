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

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Activities.Forum_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.User_Details_Activity;
import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class I_ForumTest extends ActivityTestRule<Main_Activity> {

    private Solo solo;

    public I_ForumTest() {
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
    public void testForumPost() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("My Library");
        solo.assertCurrentActivity("Wrong activity", View_My_Library_Activity.class);
        solo.clickOnText("Introduction");
        solo.assertCurrentActivity("Wrong activity", Book_Details_Activity.class);
        solo.clickOnButton("Go To Forum");
        solo.assertCurrentActivity("Wrong activity", Forum_Activity.class);
        View fab = solo.getView(R.id.addTopic);
        solo.clickOnView(fab);
//        solo.enterText((EditText) solo.getView(R.id.topic), "This is a good book");
//        solo.enterText((EditText) solo.getView(R.id.text),
//                "Great introduction book");
        solo.clickOnView(solo.getView(R.id.add));
        //assertFalse(solo.waitForText("This is a good book"));
    }

}
