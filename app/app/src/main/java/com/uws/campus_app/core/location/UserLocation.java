package com.uws.campus_app.core.location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

public class UserLocation implements LocationListener, ConnectionCallbacks, OnConnectionFailedListener {
    private UserMovedInterface userMovedInterface;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Double lat = 0.0;
    private Double lng = 0.0;

    public UserLocation(Context context) {
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context);
        builder.addOnConnectionFailedListener(this);
        builder.addApi(LocationServices.API);
        builder.addConnectionCallbacks(this);

        googleApiClient = builder.build();
        googleApiClient.connect();

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setFastestInterval(100);
        locationRequest.setInterval(2500);
    }
    
    public void setMap(UserMovedInterface map) {
        this.userMovedInterface = map;
    }

    @Override
    public void onConnectionSuspended(int ms) {
        Log.e("onConnectionSuspended", Integer.toString(ms));
    }

    @Override
    public void onLocationChanged(Location location) {
        if(location != null) {
            lng = location.getLongitude();
            lat = location.getLatitude();

            Log.i("Lat", String.valueOf(lat));
            Log.i("Lng", String.valueOf(lng));

            if(userMovedInterface != null) {
                userMovedInterface.onUserMoved(lat, lng);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i("UserLocation", "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Log.i("UserLocation", "connected");
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }
}
