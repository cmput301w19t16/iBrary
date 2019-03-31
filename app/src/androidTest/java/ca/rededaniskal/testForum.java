package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import ca.rededaniskal.Activities.Book_Details_Activity;
import ca.rededaniskal.Activities.Edit_Book_Instance_Activity;
import ca.rededaniskal.Activities.Forum_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.Activities.View_All_Users_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class testForum extends ActivityTestRule<View_All_Books_Activity> {

    private Solo solo;

    public testForum() {
        super(View_All_Books_Activity.class);
    }

    @Rule
    public ActivityTestRule<View_All_Books_Activity> rule =
            new ActivityTestRule<>(View_All_Books_Activity.class, true, true);

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
        solo.assertCurrentActivity("Wrong activity", View_All_Books_Activity.class);
        solo.clickOnText("Hairy Poter");
        solo.assertCurrentActivity("Wrong activity", Book_Details_Activity.class);
        assertTrue(solo.waitForText("Hairy Poter"));
        solo.clickOnButton("Go To Forum");
        solo.assertCurrentActivity("Wrong activity", Forum_Activity.class);
        View fab = solo.getCurrentActivity().findViewById(R.id.addTopic);
        solo.clickOnView(fab);
        solo.enterText((EditText) solo.getView(R.id.topic), "Hairy Poter is good");
        solo.enterText((EditText) solo.getView(R.id.text),
                "Hairy Poter is better than Happy Potter");
        //solo.clickOnButton("Add Forum Topic");
//        View btn = solo.getCurrentActivity().findViewById(R.id.add);
//        solo.clickOnView(btn);
        solo.clickOnView(solo.getView(R.id.add));
        assertTrue(solo.waitForText("Hairy Poter is good"));
    }
}
