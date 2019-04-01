/* TYPE:
 * Fragment Activity
 *
 * PURPOSE:
 * Notifications fragment within the main screen
 * Displays friend and borrow requests
 *
 * ISSUES:
 * Needs to link to the relevant request
 *
 */

package ca.rededaniskal.Activities.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.BusinessLogic.Notification_Adapter;
import ca.rededaniskal.BusinessLogic.myCallbackNotiList;
import ca.rededaniskal.Database.Notifications_DB;
import ca.rededaniskal.Database.Read_Notification_DB;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.EntityClasses.Request;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;
import ca.rededaniskal.Database.getUserRequestsDB;


/**
 * This fragment lets the user see all of their notifications.
 * Each notification is associated with a request, such as a friend request, or a book request.
 *
 * Todo for part 5
 * make it so that when you click on a notification, it takes you to a new
 * activity which shows the information about that notification's related request.
 * It should also retrieve notifications from the database, as well as update them.
 *
 * Currently, it only removes the "new" notification star when a notification is clicked, and
 * uses dummy data to demo.
 * It also updates the list when the list is refreshed by pulling down.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link Notifications_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notifications_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout swipeContainer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<Notification> notiList;
    private Notification_Adapter notiAdapter;

    private getUserRequestsDB db;
    private User user;
    private RecyclerView recyclerView;


    //private OnFragmentInteractionListener mListener;

    public Notifications_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notifications_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Notifications_Fragment newInstance(String param1, String param2) {
        Notifications_Fragment fragment = new Notifications_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    /*
    This function grabs the recyclerview and swipeRefreshLayout and sets the onSwipeRefresh function
     */
    //Todo: Get rid of dummy data and make it retrieve actual data.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_notifications_, container, false);

        swipeContainer = view.findViewById(R.id.swipeContainer);

        recyclerView = view.findViewById(R.id.notiRV);
        recyclerView.setHasFixedSize(true);


//        final ArrayList<Notification> notiList = new ArrayList<>();



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        updateRV(recyclerView);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                notiList.remove(0);
                updateRV(recyclerView);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeContainer.setRefreshing(false);
                    }
                }, 300); // Delay in millis

            }
        });

        return swipeContainer;
    }

    private void updateRV(final RecyclerView recyclerView){
        Notifications_DB ndb = new Notifications_DB();
        myCallbackNotiList mcbnl = new myCallbackNotiList() {
            @Override
            public void onCallback(ArrayList<Notification> notiList) {
                notiAdapter = new Notification_Adapter(notiList, Notifications_Fragment.this);
                recyclerView.setAdapter(notiAdapter);
            }
        };
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        ndb.getUsersNotifications(u.getUid(), mcbnl);
    }

    //This function takes the user back to the login page if they go back a page, as well as logs
    //them out.

    private void returnToLogin() {
        startActivity(new Intent(getActivity(), Login_Activity.class));
    }

    public void addAndUpdate(ArrayList<Notification> notification){
        Notification_Adapter notiAdapter = new Notification_Adapter(notification, Notifications_Fragment.this);
        recyclerView.setAdapter(notiAdapter);
        notiAdapter.notifyDataSetChanged();
    }

}
