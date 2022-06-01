package com.example.duan1.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.duan1.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class CustomerLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    FusedLocationProviderClient client;
    static final int PERMISSION_REQUEST_CODE = 1;
    LocationRequest request;
    LocationCallback callback;


    GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_location);
        client = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_google_map);
        mapFragment.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_DENIED) {

                String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permission, PERMISSION_REQUEST_CODE);

            } else {
                client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("addOnSuccessListener","onSuccess: "+location);
                        if(location != null){
                            Double latude  = location.getLatitude();
                            Double longitude = location.getLongitude();
                            Log.i("","onSuccess"+latude+"==="+longitude);
                        }
                    }
                });
            }
        }

        request= LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5*1000);
        callback= new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Log.d("onLocationResult","onLocationResult: " + locationResult);
                if(locationResult != null){
                    for(Location location:locationResult.getLocations()){
                        if(location != null){
                            Double latude  = location.getLatitude();
                            Double longitude = location.getLongitude();
                            Log.i("","LocationCallback"+latude+"==="+longitude);
                        }
                    }
                }
            }
        };
        client.requestLocationUpdates(request,callback,null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        client.removeLocationUpdates(callback);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    client.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            Log.d("onRequestPerResult","onSuccess: " + location);
                            if(location != null){
                                Double latude  = location.getLatitude();
                                Double longitude = location.getLongitude();
                                Log.i("","onSuccess"+latude+"==="+longitude);
                            }
                        }
                    });
                } else {
                    Toast.makeText(CustomerLocationActivity.this, "Bạn đã ko cho phép", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onMapReady(@NonNull  GoogleMap _googleMap) {
        googleMap= _googleMap;
        LatLng latLng=new LatLng(0,0);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Day la dau"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }

}