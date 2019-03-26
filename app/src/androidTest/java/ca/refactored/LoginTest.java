package ca.refactored;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ca.refactored.Activities.Login_Activity;
import ca.refactored.Activities.Main_Activity;
import ca.refactored.Activities.Signup_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class LoginTest extends ActivityTestRule<Login_Activity>{

    private Solo solo;

    public LoginTest() {
        super(Login_Activity.class);
    }

    @Rule
    public ActivityTestRule<Login_Activity> rule =
            new ActivityTestRule<>(Login_Activity.class, true, true);

    @Before
    public void setUp() throws Exception{

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void testLogin() {
        solo.assertCurrentActivity("Wrong activity", Login_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.editText5), "bob@123.ca");
        solo.enterText((EditText) solo.getView(R.id.editText6), "password");

        solo.clickOnButton("Login");

        assertTrue(solo.waitForText("Sign In Complete"));

        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
    }

    @Test
    public void testFailedLogin() {
        solo.assertCurrentActivity("Wrong activity", Login_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.editText5), "bob");
        solo.enterText((EditText) solo.getView(R.id.editText6), "word");

        solo.clickOnButton("Login");

        assertTrue(solo.waitForText("Sign In Failed"));

        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
    }

    @Test
    public void goToSignup() {
        solo.assertCurrentActivity("Wrong activity", Login_Activity.class);
        solo.clickOnButton("Sign Up");
        solo.assertCurrentActivity("Wrong activity", Signup_Activity.class);
    }
}
