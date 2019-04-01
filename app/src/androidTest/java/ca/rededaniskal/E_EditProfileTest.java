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

import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.Activities.Main_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class E_EditProfileTest extends ActivityTestRule<Edit_Profile_Activity>{

    private Solo solo;

    public E_EditProfileTest() {
        super(Edit_Profile_Activity.class);
    }

    @Rule
    public ActivityTestRule<Edit_Profile_Activity> rule =
            new ActivityTestRule<>(Edit_Profile_Activity.class, true, true);

    @Before
    public void setUp() throws Exception{

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void TestEdit() {
        solo.assertCurrentActivity("Wrong activity", Edit_Profile_Activity.class);
        solo.clearEditText((EditText) solo.getView(R.id.new_username));
        solo.enterText((EditText) solo.getView(R.id.new_username), "James Bond");
        solo.clearEditText((EditText) solo.getView(R.id.new_phone));
        solo.enterText((EditText) solo.getView(R.id.new_phone), "0070070077");
        solo.clearEditText((EditText) solo.getView(R.id.new_location));
        solo.enterText((EditText) solo.getView(R.id.new_location), "Edmonton, Alberta");
        solo.clickOnButton("Save");
        assertTrue(solo.waitForText("User account updated"));
        solo.clickOnButton("Go Home");
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
    }
}
