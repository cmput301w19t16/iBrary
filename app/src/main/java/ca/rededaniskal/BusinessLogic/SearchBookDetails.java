package ca.rededaniskal.BusinessLogic;
//Used https://github.com/google-developer-training/android-fundamentals/tree/master/WhoWroteItLoader

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchBookDetails  extends AppCompatActivity {
    // Variables for the search input field, and results TextViews
    private String returnString;


    /**
     * Initializes the activity.
     *
     * @param savedInstanceState The current state data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // Initialize all the view variables
        //mBookInput = (EditText)findViewById(R.id.bookInput);
        //mTitleText = (TextView)findViewById(R.id.titleText);
        //mAuthorText = (TextView)findViewById(R.id.authorText);

        //Check if a Loader is running, if it is, reconnect to it
        /*if(LoaderManager.getInstance(this).getLoader(0)!=null){
            LoaderManager.getInstance(this).initLoader(0,null,this);
            //getLoaderManager().initLoader(0,null,searchBooksDetails.class);
        }*/
    }

    /**
     * Gets called when the user pushes the "Search Books" button
     *
     * @param view The view (Button) that was clicked.
     */
    public ArrayList<String> searchBooks(String isbn) {
        // Get the search string from the input field.
       // String queryString = mBookInput.getText().toString();
        ArrayList strings = new ArrayList<String>();
        // Hide the keyboard when the button is pushed.
        /*InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);*/

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty,
        // add the search term to the arguments Bundle and start the loader.
        if (networkInfo != null && networkInfo.isConnected() && isbn!= null && false) {
            /*Bundle queryBundle = new Bundle();
            queryBundle.putString("isbn", isbn);
            LoaderManager.getInstance(this).restartLoader(0, queryBundle, this);*/
            returnString = NetworkUtils.getBookInfo(isbn);
            return onLoadFinished(returnString);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        else {
                strings.add("Here is title text");
                strings.add("Here is author text");
            }
            return strings;
        }


    /**
     * Loader Callbacks
     */

    /**
     * The LoaderManager calls this method when the loader is created.
     *
     * @param id ID integer to id   entify the instance of the loader.
     * @param args The bundle that contains the search parameter.
     * @return Returns a new BookLoader containing the search term.
     */
    /*
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new BookLoader(parent, isbn);
    }*/

    /**
     * Called when the data has been loaded. Gets the desired information from
     * the JSON and updates the Views.
     *
     The loader that has finished.
     * @param data The JSON response from the Books API.
     */

    public ArrayList<String> onLoadFinished(String data) {
        ArrayList<String> strings = new ArrayList<String>();
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(data);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            while (i < itemsArray.length() || (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    strings.add(volumeInfo.getString("title"));
                    strings.add(volumeInfo.getString("authors"));
                } catch (Exception e){
                    e.printStackTrace();
                }

                // Move to the next item.
                i++;
            }

            // If both are found, display the result.
            /*if (title != null && authors != null){
                mAuthorText.setText(authors);
            } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText("NoResult");
                mAuthorText.setText("");
            }*/

        } catch (Exception e){
            // If onPostExecute does not receive a proper JSON string, update the UI to show failed results.
            strings.add("Error with try");
            strings.add("Error with attempt");
            e.printStackTrace();
        }
        return strings;


    }

    /**
     * In this case there are no variables to clean up when the loader is reset.
     *
     * @param loader The loader that was reset.
     */

}


