package ca.rededaniskal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import ca.rededaniskal.R;

public class Test_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_);
        Log.d("test", "in test_activity");

        Intent returnIntent = new Intent();
        setResult(RESULT_OK,returnIntent);
        finish();
    }

}
