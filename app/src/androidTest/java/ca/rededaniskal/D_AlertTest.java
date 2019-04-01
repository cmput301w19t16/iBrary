package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.User_Details_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class D_AlertTest extends ActivityTestRule<Main_Activity> {

    private Solo solo;

    public D_AlertTest() {
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
    public void checkAlert() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Alerts");
        solo.clickOnText("Revan is now following you");
        solo.assertCurrentActivity("Wrong activity", User_Details_Activity.class);
    }
}