package ca.rededaniskal;


import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.Activities.View_Exchange_Details_Activity;
import ca.rededaniskal.Activities.View_Pending_Exchanges_Activity;
import ca.rededaniskal.Activities.View_Users_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class PendingExchangeTest extends ActivityTestRule<View_Pending_Exchanges_Activity> {

    private Solo solo;

    public PendingExchangeTest() {
        super(View_Pending_Exchanges_Activity.class);
    }

    @Rule
    public ActivityTestRule<View_Pending_Exchanges_Activity> rule =
            new ActivityTestRule<>(View_Pending_Exchanges_Activity.class, true, true);

    @Before
    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void Test() {
        solo.assertCurrentActivity("Wrong activity", View_Pending_Exchanges_Activity.class);
        solo.clickInRecyclerView(0);
        solo.assertCurrentActivity("Wrong activity", View_Exchange_Details_Activity.class);
    }
}