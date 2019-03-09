package ca.rededaniskal.Activities.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ca.rededaniskal.Activities.Edit_Profile_Activity;
import ca.rededaniskal.Activities.Login_Activity;
import ca.rededaniskal.Activities.View_Borrowed_Requested_Activity;
import ca.rededaniskal.Activities.View_Friends_Activity;
import ca.rededaniskal.Activities.View_My_Library_Activity;
import ca.rededaniskal.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link View_Own_Profile_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link View_Own_Profile_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class View_Own_Profile_Fragment extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        View v = inflater.inflate(R.layout.fragment_personal_profile, container, false);

        Button editButton = v.findViewById(R.id.edit_user);
        Button viewLibrary = (Button) v.findViewById(R.id.my_library);
        Button viewBorrowedRequested = (Button) v.findViewById(R.id.borrowed_requested_books);
        Button viewFriends = (Button) v.findViewById(R.id.friends_listbutton);
        Button logout = (Button) v.findViewById(R.id.logout);

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: need logic here to log user out
                Intent intent = new Intent(getActivity(), Login_Activity.class);
                startActivity(intent);
            }
        });

        return v;
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
}