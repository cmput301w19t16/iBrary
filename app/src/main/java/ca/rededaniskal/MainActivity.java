package ca.rededaniskal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    private Button buttonScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.go_home_button);
        buttonScanner = findViewById(R.id.GoToScannerButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(v);
            }
        });


    }

    private void goToHome(View v){
        Intent intent = new Intent(v.getContext(), AddBookInstanceActivity.class);
        startActivity(intent);
        this.finish();

    }
    private void goToScanner(View v){
        Intent intent = new Intent(v.getContext(), Barcode_Scanner_Activity.class);
        startActivity(intent);
        this.finish();
    }
}
