package com.raheelmalik.locationandstatesharing;

/**
 * Created by Raheel Malik on 1/30/2018.
 */
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

public class LocationController extends AppCompatActivity {

    // Constants:
    final int REQUEST_CODE = 123;
    final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather";
    // App ID to use OpenWeather data
    final String APP_ID = "e72ca729af228beabd5d20e3b7749713";
    // Time between location updates (5000 milliseconds or 5 seconds)
    final long MIN_TIME = 5000;
    // Distance between location updates (1000m or 1km)
    final float MIN_DISTANCE = 1000;

    // Setting LOCATION_PROVIDER here:
    String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    // Member Variables:
    TextView mCityLabel;


    // Declaring a LocationManager and a LocationListener here:
    LocationManager mLocationManager;
    LocationListener mLocationListner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_controller_layout);

        // Linking the elements in the layout to Java code
        mCityLabel = (TextView) findViewById(R.id.locationTV);







    }


    // Adding onResume() here:

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("myApplication1", "onResume() called");
        Log.d("myApplication1", "Getting current location.");
        getCurrentLocation();
    }






    private void getCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListner = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d("myApplication1", "onLocationChanged() callback received:");

                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("myApplication1","longitude is: "+longitude);
                Log.d("myApplication1","Latitude is: "+latitude);

                RequestParams params = new RequestParams();
                params.put("lat",latitude);
                params.put("lon", longitude);
                params.put("appid", APP_ID);



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Log.d("myApplication1", "onProviderDisabled() callback received:");

            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
            return;
        }
        mLocationManager.requestLocationUpdates(LOCATION_PROVIDER, MIN_TIME, MIN_DISTANCE, mLocationListner);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("myApplication1","onRequestPermissionsResult(): Permission Granted!");
                getCurrentLocation();
            }else {
                Log.d("myApplication1","Permission Denied.");
            }
        }
    }
}


