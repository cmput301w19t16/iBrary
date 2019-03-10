package ca.rededaniskal.Activities;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import ca.rededaniskal.Activities.Fragments.Notifications_Fragment;
import ca.rededaniskal.Activities.Fragments.Post_Feed_Fragment;
import ca.rededaniskal.Activities.Fragments.Search_Fragment;
import ca.rededaniskal.Activities.Fragments.View_Own_Profile_Fragment;
import ca.rededaniskal.R;
import ca.rededaniskal.BusinessLogic.SectionsPageAdapter;

public class Main_Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        mViewPager = findViewById(R.id.container);
        setUpViewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Post_Feed_Fragment(), "Feed");
        adapter.addFragment(new Search_Fragment(), "Search");
        adapter.addFragment(new View_Own_Profile_Fragment(), "Profile");
        adapter.addFragment(new Notifications_Fragment(), "Alerts");

        viewPager.setAdapter(adapter);


    }



}