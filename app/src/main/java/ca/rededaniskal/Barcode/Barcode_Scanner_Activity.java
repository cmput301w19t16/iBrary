package ca.rededaniskal.Barcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import ca.rededaniskal.Activities.Add_Book_To_Library_Activity;
import ca.rededaniskal.R;

import java.util.List;

public class Barcode_Scanner_Activity extends AppCompatActivity implements View.OnClickListener, Barcode_Reader_Fragment.BarcodeReaderListener {

    private TextView mTvResult;
    private Class returnTo;

    /*public Barcode_Scanner_Activity(Class returnTo){
        this.returnTo = returnTo;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode__scanner_);
        returnTo = getIntent().getSerializableExtra("ReturnClass").getClass();
        findViewById(R.id.btn_fragment).setOnClickListener(this);
        //mTvResult = findViewById(R.id.tv_result);

    }

    private void addBarcodeReaderFragment() {
        Barcode_Reader_Fragment readerFragment = Barcode_Reader_Fragment.newInstance(true, false, View.VISIBLE);
        readerFragment.setListener(this);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fm_container, readerFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_fragment)
            addBarcodeReaderFragment();
    }


    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "error in  scanning", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onScanned(Barcode barcode) {
        Intent intent = new Intent(this, returnTo);
        intent.putExtra("ISBN", barcode.rawValue);
        Toast.makeText(this, barcode.rawValue, Toast.LENGTH_SHORT).show();
        //mTvResult.setText(barcode.rawValue);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

    }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_LONG).show();
    }
}
