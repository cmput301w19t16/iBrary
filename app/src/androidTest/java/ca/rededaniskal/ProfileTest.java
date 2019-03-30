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
import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.View_All_Requests_Activity;
import ca.rededaniskal.Activities.View_All_Users_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.Activities.View_Pending_Exchanges_Activity;
import ca.rededaniskal.Activities.View_Users_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(AndroidJUnit4.class)
public class ProfileTest extends ActivityTestRule<Main_Activity>{

    private Solo solo;

    public ProfileTest() {
        super(Main_Activity.class);
    }

    @Rule
    public ActivityTestRule<Main_Activity> rule =
            new ActivityTestRule<>(Main_Activity.class, true, true);

    @Before
    public void setUp() throws Exception{

        solo = new Solo(getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    @Test
    public void TestEditProfile() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("Edit Profile");
        solo.assertCurrentActivity("Wrong activity", Edit_Profile_Activity.class);
    }

    @Test
    public void TestFollowers() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("My Followers");
        solo.assertCurrentActivity("Wrong activity", View_Users_Activity.class);
    }

    @Test
    public void TestMyLibrary() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("My Library");
        solo.assertCurrentActivity("Wrong activity", View_My_Library_Activity.class);
    }

    @Test
    public void TestPendingExchange() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("Pending Exchanges");
        solo.assertCurrentActivity("Wrong activity", View_Pending_Exchanges_Activity.class);
    }

//    @Test
//    public void TestBorrowRequestBooks() {
//        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
//        solo.clickOnText("Profile");
//        solo.clickOnButton("Borrowed/Requested Books");
//        solo.assertCurrentActivity("Wrong activity", View_Borrowed_Requested_Activity.class);
//    }

    @Test
    public void TestAllBookRequests() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("All Book Requests");
        solo.assertCurrentActivity("Wrong activity", View_All_Requests_Activity.class);

    }

    @Test
    public void TestAllUsers() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("View All Users");
        solo.assertCurrentActivity("Wrong activity", View_All_Users_Activity.class);
    }

    @Test
    public void TestLogout() {
        solo.assertCurrentActivity("Wrong activity", Main_Activity.class);
        solo.clickOnText("Profile");
        solo.clickOnButton("Logout");
        solo.assertCurrentActivity("Wrong activity", Login_Activity.class);
    }
}
