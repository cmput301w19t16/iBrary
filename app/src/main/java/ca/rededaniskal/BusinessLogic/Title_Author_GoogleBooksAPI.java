package ca.rededaniskal.BusinessLogic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import android.widget.ImageView;

import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;


/**
 * Received ISBN from Barcode Scanner. Send to GoogleBooks to obtain book information.
 *
 * @author Daniela, modified https://stackoverflow.com/questions/14571478/using-google-books-api-in-android
 */
public class Title_Author_GoogleBooksAPI extends AsyncTask<String, Object, JSONObject> {

    private Context context;
    private TextView myTitle;
    private TextView myAuthor;

    private Bitmap googleCover;
    public AsyncResponse delegate;
    //private Class fromClass;
    private ConnectivityManager myConnectivityManager;

    public Title_Author_GoogleBooksAPI(Context context, TextView title, TextView author) {
        this.context = context;
        this.myTitle = title;
        this.myAuthor = author;
    }

    @Override
    protected void onPreExecute() {
        // Check network connection.
        if (isNetworkConnected() == false) {
            // Cancel request.
            Log.i(getClass().getName(), "Not connected to the internet");
            cancel(true);
            return;
        }
    }

    @Override
    protected JSONObject doInBackground(String... isbns) {
        // Stop if cancelled
        if (isCancelled()) {
            return null;
        }

        String apiUrlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbns[0];
        try {
            HttpURLConnection connection = null;
            // Build Connection.
            try {
                URL url = new URL(apiUrlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(5000); // 5 seconds
                connection.setConnectTimeout(5000); // 5 seconds
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                // Impossible: "GET" is a perfectly valid request method.
                e.printStackTrace();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                Log.w(getClass().getName(), "GoogleBooksAPI request failed. Response Code: " + responseCode);
                connection.disconnect();
                return null;
            }

            // Read data from response.
            StringBuilder builder = new StringBuilder();
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = responseReader.readLine();
            while (line != null) {
                builder.append(line);
                line = responseReader.readLine();
            }
            String responseString = builder.toString();
            Log.d(getClass().getName(), "Response String: " + responseString);
            JSONObject responseJson = new JSONObject(responseString);
            // Close connection and return response code.
            connection.disconnect();
            return responseJson;
        } catch (SocketTimeoutException e) {
            Log.w(getClass().getName(), "Connection timed out. Returning null");
            return null;
        } catch (IOException e) {
            Log.d(getClass().getName(), "IOException when connecting to Google Books API.");
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            Log.d(getClass().getName(), "JSONException when connecting to Google Books API.");
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(JSONObject responseJson) {
        if (!isCancelled() && responseJson != null) {
            String title = null;
            JSONArray authors = null;

            if (responseJson.has("imageLinks")) {
                try {
                    JSONObject imageInfo = responseJson.getJSONObject("imageLinks");
                    String thumbURLs = imageInfo.getString("smallThumbnail");
                    URL thumbURL = new URL(thumbURLs);
                    URLConnection thumbConn = thumbURL.openConnection();
                    thumbConn.connect();

                    InputStream thumbIn = thumbConn.getInputStream();
                    BufferedInputStream thumbBuff = new BufferedInputStream(thumbIn);

                    googleCover = BitmapFactory.decodeStream(thumbBuff);

                    thumbBuff.close();
                    thumbIn.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (responseJson.has("items")){
                try {
                    // Get appropriate fields out of JSON object.
                    JSONArray itemsArray = responseJson.getJSONArray("items");
                    JSONObject book = itemsArray.getJSONObject(0);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getJSONArray("authors");

                    // If both are found, display the result.
                    if (title != null && authors != null) {
                        for (int i = 0; i < authors.length(); i++) {
                            myAuthor.append(authors.get(i).toString());
                        }
                        myTitle.setText(title);
                    }


                    //delegate.processFinish(googleCover);
                } catch (Exception e) {
                    // If onPostExecute does not receive a proper JSON string
                    Log.d("Reach exception in onPostExecute", e.toString());
                    e.printStackTrace();
                    //delegate.processFinish(null);
                }
            }
        }
        delegate.processFinish(googleCover);
    }

    protected boolean isNetworkConnected() {

        // Instantiate mConnectivityManager if necessary
        if (myConnectivityManager == null) {
            myConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        // Is device connected to the Internet?
        NetworkInfo networkInfo = myConnectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
}

