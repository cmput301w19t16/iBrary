//Modified by based off https://github.com/google-developer-training/android-fundamentals/blob/master/WhoWroteIt/app/src/main/java/com/example/android/whowroteit/FetchBook.java
/*
 * Copyright (C) 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.rededaniskal.BusinessLogic;

        import android.app.Activity;
        import android.content.Context;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.IBinder;
        import android.util.Log;
        import android.view.View;
        import android.view.inputmethod.InputMethodManager;
        import android.widget.EditText;
        import android.widget.TextView;

        import org.json.JSONArray;
        import org.json.JSONObject;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import ca.rededaniskal.R;

        import static android.util.Log.e;

/**
 * AsyncTask implementation that opens a network connection and
 * query's the Book Service API.
 */
public class FetchBook extends Activity {

    // Variables for the results TextViews
    private TextView mTitleText;
    private TextView mAuthorText;
    private String ISBN;

    // Class name for Log tag
    private static final String LOG_TAG = FetchBook.class.getSimpleName();

    public FetchBook(TextView titleText,TextView authorText, String ISBN) {
        this.ISBN = ISBN;
        this.mTitleText = titleText;
        this.mAuthorText = authorText;
    }
    /*@Override
    protected void onStartLoading() {
        forceLoad(); // Starts the loadInBackground method
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // Get the search string from the input field.
        //String queryString = mBookInput.getText().toString();

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        //https://www.programcreek.com/java-api-examples/?class=android.view.View&method=getWindowToken
        View currentFocus = getCurrentFocus();
        if (currentFocus != null){
            // Base interface for a remotable object
            IBinder windowToken = currentFocus.getWindowToken();

            // Hide type
            int hideType = InputMethodManager.HIDE_NOT_ALWAYS;

            // Hide the KeyBoard
            inputManager.hideSoftInputFromWindow(windowToken, hideType);
        }


        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty, start a FetchBook AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && ISBN != null) {
            String url = createURL(ISBN);
            new MyTask().execute(url);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        /*else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }*/
    }

    private String createURL(String ISBN){
        return "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN + "&key=AIzaSyDnb2g1cRtlMB-h-yi3_XwrYFqcvwBmBLA";
    }

    private class MyTask extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            //Log.e("AsyncTask", "onPreExecute");
        }

        /**
         * Makes the Books API call off of the UI thread.
         *
         * @param params String array containing the search data.
         * @return Returns the JSON string from the Books API or
         * null if the connection failed.
         */
        @Override
        protected String doInBackground(String... params) {

            // Get the search string
            String queryString = params[0];


            // Set up variables for the try block that need to be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String bookJSONString = null;

            // Attempt to query the Books API.
            try { /*
                // Base URI for the Books API.
                final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";

                final String QUERY_PARAM = "q"; // Parameter for the search string.
                //final String MAX_RESULTS = "maxResults"; // Parameter that limits search results.
                //final String PRINT_TYPE = "printType"; // Parameter to filter by print type.
                final String givenISBN = ISBN;

                // Build up your query URI, limiting results to 10 items and printed books.
                Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                        .appendQueryParameter(QUERY_PARAM, queryString)
                        //.appendQueryParameter(MAX_RESULTS, "10")
                        //.appendQueryParameter(PRINT_TYPE, "books")
                        .appendQueryParameter(givenISBN, queryString)
                        .build();
                        */

                URL requestURL = new URL(queryString);

                // Open the network connection.
                urlConnection = (HttpURLConnection) requestURL.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Get the InputStream.
                InputStream inputStream = urlConnection.getInputStream();

                // Read the response string into a StringBuilder.
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // but it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                    builder.append(line + "\n");
                }

                if (builder.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    // return null;
                    return null;
                }
                bookJSONString = builder.toString();

                // Catch errors.
            } catch (IOException e) {
                e("fetchBookByISBN", e.toString());
                e.printStackTrace();

                // Close the connections.
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            // Return the raw response.
            return bookJSONString;
        }

        /**
         * Handles the results on the UI thread. Gets the information from
         * the JSON and updates the Views.
         *
         * @param s Result from the doInBackground method containing the raw JSON response,
         *          or null if it failed.
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                // Convert the response into a JSON object.
                JSONObject jsonObject = new JSONObject(s);
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
                        title = volumeInfo.getString("title");
                        authors = volumeInfo.getString("authors");
                    } catch (Exception e) {
                        Log.v("postExecuteMsg", "didn't get author/title");
                        e.printStackTrace();
                    }

                    // Move to the next item.
                    i++;
                }

                // If both are found, display the result.
                if (title != null && authors != null) {
                    mTitleText.setText(title);
                    mAuthorText.setText(authors);
                } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText("No results");
                mAuthorText.setText("No results");
            }

            } catch (Exception e) {
                // If onPostExecute does not receive a proper JSON string,
                // update the UI to show failed results.
           /* mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");*/

                e.printStackTrace();
            }
        }
    }
}