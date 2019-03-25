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
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import ca.rededaniskal.Activities.Filter_My_Books_Logic;
import ca.rededaniskal.BusinessLogic.BookAdapter;
import ca.rededaniskal.BusinessLogic.PostAdapter;
import ca.rededaniskal.BusinessLogic.Search_Logic;
import ca.rededaniskal.EntityClasses.Book_List;
import ca.rededaniskal.EntityClasses.Master_Book;
import ca.rededaniskal.EntityClasses.Post;
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


    Button searchBy;
    String[] filterOptions;
    boolean[] selectedOptions;
    ArrayList<Integer> chosenOptions = new ArrayList<>();

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

        final View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchBy = (Button) view.findViewById(R.id.FilterSearchFragmentButton);
        filterOptions = getResources().getStringArray(R.array.filter_search_options);
        selectedOptions = new boolean[filterOptions.length];
        final RecyclerView recyclerView = view.findViewById(R.id.display);
        recyclerView.setHasFixedSize(true);
        final ArrayList<Master_Book> master_books = new ArrayList<Master_Book>();



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
                        //mItemSelected.setText(item);
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
        return view;
}

    public void updateBookView(ArrayList<Master_Book> master_list, RecyclerView recyclerView){
        //uses filter book logic to allow users to filter books by filter options
        if (chosenOptions.size()!=0){
            Search_Logic sl = new Search_Logic(this, chosenOptions,"");



        }
        else {


        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //final BookAdapter bookAdapter = new BookAdapter(master_list, Search_Fragment.this);
        //recyclerView.setAdapter(bookAdapter);


    }



}
