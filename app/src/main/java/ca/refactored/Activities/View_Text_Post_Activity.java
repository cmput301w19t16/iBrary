/* TYPE:
 * Activity
 *
 * PURPOSE:
 * View a Text post
 *
 * ISSUES:
 * Needs DB support
 *
 */
package ca.refactored.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ca.refactored.R;

//Activity which represents a text post
public class View_Text_Post_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__text__post_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
