package ca.rededaniskal.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import ca.rededaniskal.R;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
// Code adapted from https://stackoverflow.com/a/28389578



public class Set_Location_Activity extends Activity implements OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener {

    private FusedLocationProviderClient client;

    LatLng position = new LatLng(34.6767, 33.04455);
    private Marker marker = null;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.
                        map);


        mapFragment.getMapAsync(this);

        FloatingActionButton findme = findViewById(R.id.findme);
        FloatingActionButton confirm = findViewById(R.id.confirm);

        client = LocationServices.getFusedLocationProviderClient(this);

        findme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(Set_Location_Activity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Location location = task.getResult();
                            position =  new LatLng(location.getLatitude(), location.getLongitude());


                            if (marker == null){
                                marker =   mMap.addMarker(new MarkerOptions()
                                        .title("Shop")
                                        .snippet("Is this the right location?")
                                        .position(position));
                                marker.setDraggable(true);


                            }else{
                                marker.setPosition(position);
                            }

                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13));
                            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
                            mMap.animateCamera(zoom);
                        }
                    }
                });
            }
        });


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marker != null){

                    Intent returnIntent = new Intent();
                    Bundle b = new Bundle();
                    b.putDouble("lat",position.latitude);
                    b.putDouble("lng", position.longitude);

                    returnIntent.putExtras(b);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }else{
                    Toast.makeText(Set_Location_Activity.this.getApplicationContext(), "Please place a marker", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        requestPermission();

        if (ActivityCompat.checkSelfPermission(Set_Location_Activity.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);
        map.setOnInfoWindowClickListener(this);
        map.setOnMarkerDragListener(this);
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        LatLng position0 = marker.getPosition();
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        LatLng position0 = marker.getPosition();
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        position = marker.getPosition();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[] {ACCESS_FINE_LOCATION}, 1);
    }
}