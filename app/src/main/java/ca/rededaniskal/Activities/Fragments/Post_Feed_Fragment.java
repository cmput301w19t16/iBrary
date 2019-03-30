/* TYPE:
 * Fragment Activity
 *
 * PURPOSE:
 * View activity feed of you friends (forum posts, etc)
 *
 * ISSUES:
 * Needs DB support
 *
 */
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

import ca.rededaniskal.EntityClasses.Post;
import ca.rededaniskal.BusinessLogic.PostAdapter;
import ca.rededaniskal.R;

/**
 * This fragment is to view the activities of your friends. It ties very closely with our "wow"
 * factor, since it aims to make viewing forums and finding new books more easy.
 *
 * This fragment shows posts with updates from your friends, such as adding books, reviewing books,
 * as well as showing posts from forums you are subscribed to.
 *
 * Clicking on a post takes you to a new screen which lets you see the full post, as well as
 * see the comments and comment on the post.
 *
 * Todo for part 5
 * Make it so that posts load from the database.
 * Add forum posts.
 *
 * What we have so far:
 * Shows list of posts, which can be clicked on to go to new activity
 * List updates when the user pulls down at the top of the list.
 */


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Post_Feed_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Post_Feed_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SwipeRefreshLayout swipeContainer;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //OnFragmentInteractionListener mListener;

    public Post_Feed_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Post_Feed_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Post_Feed_Fragment newInstance(String param1, String param2) {
        Post_Feed_Fragment fragment = new Post_Feed_Fragment();
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

    //This function gets the recyclerview and refreshlayout, and fills it with data.
    //Todo: make this stop using dummy data and instead get it from the database.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View view = inflater.inflate(R.layout.fragment_feed, container, false);

        swipeContainer = view.findViewById(R.id.swipeContainer);


        final RecyclerView recyclerView = view.findViewById(R.id.feedRV);
        recyclerView.setHasFixedSize(true);
        final ArrayList<Post> postList = new ArrayList<Post>();

        postList.add(new Post("Loved this Book!", "Nick", "Happy Potter", "Thoughts on Book"));
        postList.add(new Post("Can I borrow this from anyone?", "Revan", "Oxford English Dictionary", "Looking to lend this"));
        postList.add(new Post("Luminous!", "Skype", "Happy Potter", "Thoughts on Book"));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final PostAdapter postAdapter = new PostAdapter(Post_Feed_Fragment.this, postList);
        recyclerView.setAdapter(postAdapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postList.add(new Post("This post should now show up",
                        "blank", "blank", "Display"));
                recyclerView.setAdapter(new PostAdapter(Post_Feed_Fragment.this, postList));
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        swipeContainer.setRefreshing(false);
                    }
                }, 300); // Delay in millis

            }
        });

        return swipeContainer;
    }
}
