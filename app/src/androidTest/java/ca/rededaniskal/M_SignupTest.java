package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.ListView;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.beans.IndexedPropertyChangeEvent;

import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.Signup_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class M_SignupTest extends ActivityTestRule<Signup_Activity>{

    private Solo solo;

    public M_SignupTest() {
        super(Signup_Activity.class);
    }

    @Rule
    public ActivityTestRule<Signup_Activity> rule =
            new ActivityTestRule<>(Signup_Activity.class, true, true);

    @Before
    public void setUp() throws Exception{

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void checkDuplicateSignup() {
        solo.assertCurrentActivity("Wrong activity", Signup_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.input_username), "Joe Smith");
        solo.enterText((EditText) solo.getView(R.id.input_password), "password");
        solo.enterText((EditText) solo.getView(R.id.input_confirm_password), "password");
        solo.enterText((EditText) solo.getView(R.id.input_email), "joe@smith.com");
        solo.enterText((EditText) solo.getView(R.id.input_phone), "1234567890");

        solo.clickOnButton("Create Account");

        solo.assertCurrentActivity("Wrong activity", Signup_Activity.class);
    }

    @Test
    public void checkSignup() {
        solo.assertCurrentActivity("Wrong activity", Signup_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.input_username), "James Bond");
        solo.enterText((EditText) solo.getView(R.id.input_password), "password");
        solo.enterText((EditText) solo.getView(R.id.input_confirm_password), "password");
        solo.enterText((EditText) solo.getView(R.id.input_email), "james@bond.com");
        solo.enterText((EditText) solo.getView(R.id.input_phone), "1234567890");

        solo.clickOnButton("Create Account");

        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
    }

}