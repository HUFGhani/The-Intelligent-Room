package com.project.smarthome.smarthome.Code.Location;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class Locator implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final String TAG = "LocationHelper";
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private int mUpdateIntervalMs;
    private Location mLastLocation;

    public Locator() throws InstantiationException {
        throw new InstantiationException("Missing parameters: context and updateInterval");
    }

    public Locator(Context context, int updateIntervalMs) throws InstantiationException {
        mContext = context;
        mUpdateIntervalMs = updateIntervalMs;
        if (!checkLocationPermission()) {
            throw new InstantiationException("Location permissions not granted");
        }
        createGoogleApiClient();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        createLocationRequest();
        if (checkLocationPermission()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Required override
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
    }

    public Location getLocation() {
        return mLastLocation;
    }

    private void createGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    public void disconnectGoogleApiClient() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            }
            mGoogleApiClient.disconnect();
        }
    }

    private void createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(mUpdateIntervalMs);
            mLocationRequest.setFastestInterval(mUpdateIntervalMs);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
    }

    private boolean checkLocationPermission() {
        return (checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

}
