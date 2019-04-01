/* TYPE:
 * Fragment Activity
 *
 * PURPOSE:
 * Search Books and users from the main screen
 *
 * ISSUES:
 * Needs DB support and filtering
 *
 */
//Tutorial for selecting more than one option in search
//https://www.youtube.com/redirect?q=https%3A%2F%2Fgithub.com%2Fcodingdemos%2FMultichoiceTutorial&redir_token=zWJM5OoUtOrwMvfLlGWm1qv4-B98MTU1MjE5NTgxMEAxNTUyMTA5NDEw&event=video_description&v=wfADRuyul04
package ca.rededaniskal.Activities.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.LinkedHashSet;

import ca.rededaniskal.BusinessLogic.Master_BookAdapter;
import ca.rededaniskal.BusinessLogic.Search_Logic;
import ca.rededaniskal.BusinessLogic.UserAdapter;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.User;
import ca.rededaniskal.R;

/**
 * This fragment is for finding books, users, and forums.
 *
 * Todo for part 5
 * Completely implement this whole fragment.
 *
 * What we have so far:
 * A button which lets you choose what to search by
 * A field for search text
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link Search_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Search_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;
    private Button searchBy;
    private String[] filterOptions;
    private boolean[] selectedOptions;
    private ArrayList<Integer> chosenOptions = new ArrayList<>();
    private SearchView searchString;

    private RecyclerView display;
    private Master_BookAdapter MB_adapter;
    private UserAdapter User_adapter;
    private LayoutInflater inflater;
    private ViewGroup container;
    private View dbView;
    private Search_Fragment search_fragment  = this;
    private SwipeRefreshLayout swipeContainer;
    private View view;
    private String queryString;
    private ArrayList<Master_Book> viewBookList;
    private ArrayList<User> viewUserList;

    public Search_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Search_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Search_Fragment newInstance(String param1, String param2) {
        Search_Fragment fragment = new Search_Fragment();
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

        this.inflater = inflater;
        this.container = container;
        view = inflater.inflate(R.layout.fragment_search, container, false);
        swipeContainer = view.findViewById(R.id.swipeContainersearch);

        dbView = view;
        viewBookList = new ArrayList<>();
        viewUserList = new ArrayList<>();

        searchString = view.findViewById(R.id.fragmentSearchView);
        searchString.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewBookList.clear();
                Log.d("Searchlog", "**************querylisten");

                new Search_Logic(search_fragment, chosenOptions,query);
                queryString = query;
                searchString.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewBookList.clear();
                queryString = newText;

                return false;
            }
        });

        searchBy = (Button) view.findViewById(R.id.FilterSearchFragmentButton);
        filterOptions = getResources().getStringArray(R.array.filter_search_options);
        selectedOptions = new boolean[filterOptions.length];


        searchBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.search_by);
                builder.setMultiChoiceItems(filterOptions, selectedOptions, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            chosenOptions.add(position);
                        }else{
                            chosenOptions.remove((Integer.valueOf(position)));
                        }
                    }
                });

                builder.setCancelable(false);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < chosenOptions.size(); i++) {
                            item = item + filterOptions[chosenOptions.get(i)];
                            if (i != chosenOptions.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        viewBookList.clear();
                    }
                });

                builder.setNegativeButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton(R.string.clear_all, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < selectedOptions.length; i++) {
                            selectedOptions[i] = false;
                            chosenOptions.clear();
                            //mItemSelected.setText("");
                        }
                    }
                });

                AlertDialog mDialog = builder.create();
                mDialog.show();
            }
        });


        // Inflate the layout for this fragment
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewBookList.clear();
                Log.d("Searchlog", "**************Onrefresh");

                new Search_Logic(search_fragment, chosenOptions, queryString );
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeContainer.setRefreshing(false);
                    }
                }, 300); // Delay in millis

            }
        });

        return view;
    }


    public void update_books(ArrayList<Master_Book> master_books){
        viewBookList = master_books;

        display = dbView.findViewById(R.id.display);
        display.setHasFixedSize(true);
        display.setLayoutManager(new LinearLayoutManager(getContext()));

        MB_adapter = new Master_BookAdapter(Search_Fragment.this, master_books);
        display.setAdapter(MB_adapter);
    }


    public void update_users(ArrayList<User> users){
        display = dbView.findViewById(R.id.display);
        display.setHasFixedSize(true);
        display.setLayoutManager(new LinearLayoutManager(getContext()));

        User_adapter = new UserAdapter(getActivity(), users);
        display.setAdapter(MB_adapter);
    }

    public void addBookToAdapter(Master_Book m){
        viewBookList.add(m);
        LinkedHashSet<Master_Book> remove = new LinkedHashSet<>(viewBookList);
        viewBookList = new ArrayList<>(remove);
        update_books(viewBookList);
    }
  
    public void addBookToAdapter(ArrayList<Master_Book> m){
        viewBookList.addAll(m);
        update_books(viewBookList);
    }
}
