package ca.rededaniskal.BusinessLogic;

//https://stackoverflow.com/questions/38137689/how-to-pull-image-from-url-and-convert-into-bitmap-in-android
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Used to load an image from the url
 */

public class LoadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public LoadImage(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap cover = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            cover = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return cover;
    }

    protected void onPostExecute(Bitmap result) {

//        mImage.setBackground(result);
        bmImage.setImageBitmap(result);
    }
}
