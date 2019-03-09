package ca.rededaniskal.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


public class Data_Provider extends ContentProvider {


    @Override
    public boolean onCreate() {
        FirebaseOptions.Builder builder = new FirebaseOptions.Builder()
                .setApplicationId("1:212907668448:android:01929685eb1c815e")
                .setApiKey("AIzaSyAKjxhAk_vkugEaKR2DmfGS9v6A-WY0Nco")
                .setDatabaseUrl("https://ibrary.firebaseio.com")
                .setStorageBucket("ibrary.appspot.com");
        FirebaseApp.initializeApp(getContext(), builder.build());
        return false;
    }

    @androidx.annotation.Nullable
    @Override
    public Cursor query(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String[] projection, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs, @androidx.annotation.Nullable String sortOrder) {
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public String getType(@androidx.annotation.NonNull Uri uri) {
        return null;
    }

    @androidx.annotation.Nullable
    @Override
    public Uri insert(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@androidx.annotation.NonNull Uri uri, @androidx.annotation.Nullable ContentValues values, @androidx.annotation.Nullable String selection, @androidx.annotation.Nullable String[] selectionArgs) {
        return 0;
    }
}
