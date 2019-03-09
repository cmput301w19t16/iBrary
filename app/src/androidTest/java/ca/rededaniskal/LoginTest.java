package ca.rededaniskal;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.widget.EditText;

import com.robotium.solo.Solo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.Signup_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;

public class LoginTest extends ActivityTestRule<Login_Activity> {

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
    public void checkLogin() {
        solo.assertCurrentActivity("Wrong activity", Login_Activity.class);
        solo.enterText((EditText) solo.getView(R.id.editText5), "Joe Smith");
        solo.enterText((EditText) solo.getView(R.id.editText6), "password1");

        solo.clickOnButton("Login");
    }

}
