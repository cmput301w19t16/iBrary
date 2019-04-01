package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.User_Details_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class H_BorrowedRequestedTest extends ActivityTestRule<View_Borrowed_Requested_Activity> {

    private Solo solo;

    public H_BorrowedRequestedTest() {
        super(View_Borrowed_Requested_Activity.class);
    }

    @Rule
    public ActivityTestRule<View_Borrowed_Requested_Activity> rule =
            new ActivityTestRule<>(View_Borrowed_Requested_Activity.class, true, true);

    @Before
    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void testBooks() {
        solo.assertCurrentActivity("Wrong activity", View_Borrowed_Requested_Activity.class);
        View toggleRequested = solo.getView(R.id.Requested);
        solo.clickOnView(toggleRequested);
        assertTrue(solo.waitForText("City"));
    }
}
