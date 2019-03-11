/* TYPE:
 * Fragment
 *
 * PURPOSE:
 * View your own info
 * Navigate to editing your profile, viewing library, seeing borrowed and requested books and friends
 *
 * ISSUES:
 *
 */
package ca.rededaniskal.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.View_All_Books_Activity;
import ca.rededaniskal.Activities.View_All_Requests_Activity;
import ca.rededaniskal.Activities.Main_Activity;
import ca.rededaniskal.Activities.View_All_Users_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.Activities.View_Friends_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link View_Own_Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class View_Own_Profile_Fragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private currentUserDetailsDB db;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;

//    private OnFragmentInteractionListener mListener;

    public View_Own_Profile_Fragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment View_Own_Profile_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static View_Own_Profile_Fragment newInstance(String param1, String param2) {
        View_Own_Profile_Fragment fragment = new View_Own_Profile_Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "*********----->onCreateView");


        v = inflater.inflate(R.layout.fragment_personal_profile, container, false);
        db = new currentUserDetailsDB();

        Button editButton = v.findViewById(R.id.edit_user);
        Button viewLibrary = (Button) v.findViewById(R.id.my_library);
        Button viewBorrowedRequested = (Button) v.findViewById(R.id.borrowed_requested_books);
        Button viewFriends = (Button) v.findViewById(R.id.friends_listbutton);
        Button logout = (Button) v.findViewById(R.id.logout);
        Button viewAllRequests = (Button) v.findViewById(R.id.view_all_requests);
        Button viewAllUsers = (Button) v.findViewById(R.id.viewUsers);
        Button viewAllBooks = (Button) v.findViewById(R.id.viewBooks);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Edit_Profile_Activity.class);
                startActivity(intent);
            }
        });

        viewLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_My_Library_Activity.class);
                startActivity(intent);
            }
        });

        viewBorrowedRequested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_Borrowed_Requested_Activity.class);
                startActivity(intent);
            }
        });

        viewFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_Friends_Activity.class);
                startActivity(intent);
            }
        });

        viewAllRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_All_Requests_Activity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), Login_Activity.class);
                startActivity(intent);
            }
        });

        viewAllUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_All_Users_Activity.class);
                startActivity(intent);
            }
        });

        viewAllBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), View_All_Books_Activity.class);
                startActivity(intent);
            }
        });

        return v;
    }


    public void setTexts(String email, String phone, String location, String username){
        Log.d(TAG, "*********----->setTexts");

        TextView e = v.findViewById(R.id.DisplayEmail);
        TextView p = v.findViewById(R.id.DisplayPhoneNumber);
        TextView l = v.findViewById(R.id.DisplayLocation);
        TextView u = v.findViewById(R.id.DisplayUserName);

        e.setText(email);
        p.setText(phone);
        l.setText(location);
        u.setText(username);
    }

    /**
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

/**
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    /
 */

    private void returnToLogin() {
        startActivity(new Intent(getActivity(), Login_Activity.class));
    }

    public class currentUserDetailsDB{
        private FirebaseAuth mAuth;
        private String email;
        private String username;
        private String location;
        private String phone;
        private FirebaseUser user;
        private DatabaseReference mDatabase;
        private List<User> userList;

        public currentUserDetailsDB() {
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
            userList = new ArrayList<>();
            if (user != null) {
                email = user.getEmail();
                getUserDetails();

            } else {
                returnToLogin();

            }
        }

        private void getUserDetails(){
            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            Query query = FirebaseDatabase.getInstance().getReference("Users")
                    .orderByChild("email")
                    .equalTo(email);

            Log.d(TAG, "*********----->"+email);
            query.addListenerForSingleValueEvent(valueEventListener);

        }

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();
                Log.d(TAG, "*********----->onDataChange");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "*********----->exists");
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        //Log.d(TAG, "*********----->"+snapshot.getValue());
                        User user = snapshot.getValue(User.class);
                        setTexts(user.getEmail(), user.getPhoneNumber(), user.getLocation(), user.getUserName());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

    }

}
