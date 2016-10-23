package com.example.admin.tenton;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CheckInActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnCheckIn;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.checkInFragment);
        mapFragment.getMapAsync(this);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
//        final double tentonLatitude = 42.641004;
//        final double tentonLongitude = 21.104819;

        //Tenton Location
        final double tentonLatitude = 42.643840;
        final double tentonLongitude = 21.155530;

        btnCheckIn = (Button) findViewById(R.id.btnCheckIn);
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(CheckInActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CheckInActivity.this,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= 23 && ((ContextCompat.checkSelfPermission(CheckInActivity.this,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(CheckInActivity.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                1) ;
                    }
                    else {

                    }
                    return;
                }

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true));

                if (location!= null){
                    float[] distance = new float[2];
                    Circle circle = mMap.addCircle(new CircleOptions()
                            .center(new LatLng(tentonLatitude, tentonLongitude))
                            .radius(15)
                            .strokeColor(0x00000000)
                            .strokeWidth(1)
                            .fillColor(0x00000000));

                    Location.distanceBetween(location.getLatitude(), location.getLongitude(),
                            circle.getCenter().latitude, circle.getCenter().longitude, distance);

                    if (distance[0] > circle.getRadius()){
                        Toast.makeText(getBaseContext(), "Outside: "+ location.getLatitude() + ", "
                                + location.getLongitude(), Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "Inside: "+ location.getLatitude() + ", "
                                + location.getLongitude(), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Couldn't get your Location", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Circle circle = mMap.addCircle(new CircleOptions()
                .center(new LatLng(tentonLatitude, tentonLongitude))
                .radius(17)
                .strokeColor(0xffff0000)
                .strokeWidth(1)
                .fillColor(0x40ff0000));

        //Add a marker in Sydney and move the camera
        LatLng prishtina = new LatLng(tentonLatitude, tentonLongitude);
        Marker marker = mMap.addMarker(new MarkerOptions().position(prishtina).title("Tenton Office, Prishtina"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(prishtina));


        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(tentonLatitude, tentonLongitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(18);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);


        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);



    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CheckIn Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(CheckInActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }
}
