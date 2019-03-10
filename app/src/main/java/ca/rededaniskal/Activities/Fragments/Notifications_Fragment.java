package ca.rededaniskal.Activities.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ca.rededaniskal.BusinessLogic.Notification_Adapter;
import ca.rededaniskal.EntityClasses.Notification;
import ca.rededaniskal.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notifications_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_notifications_, container, false);

        swipeContainer = view.findViewById(R.id.swipeContainer);

        final RecyclerView recyclerView = view.findViewById(R.id.notiRV);
        recyclerView.setHasFixedSize(true);

        final ArrayList<Notification> notiList = new ArrayList<>();

        Notification n = new Notification("You", "notiID", false);
        n.setRequestType("Friend_Request");
        notiList.add(n);

        Notification i = new Notification("You", "notiID", true);
        i.setRequestType("Borrow_Request");
        notiList.add(i);

        Notification j = new Notification("You", "notiID", true);
        j.setRequestType("Return_Request");
        notiList.add(j);

        Notification k = new Notification("You", "notiID", false);
        k.setRequestType("Friend_Request");
        notiList.add(k);

        final Notification p = new Notification("You", "notiID", false);
        p.setRequestType("something not accounted for");
        notiList.add(p);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final Notification_Adapter notiAdapter = new Notification_Adapter(notiList, Notifications_Fragment.this);
        recyclerView.setAdapter(notiAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                notiList.remove(0);
                recyclerView.setAdapter(new Notification_Adapter(notiList, Notifications_Fragment.this));
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

    /**
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    /*
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
     *
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
