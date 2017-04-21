package com.project.smarthome.smarthome.Code.Location;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.project.smarthome.smarthome.Code.Constants;

import java.util.Collections;
import java.util.List;

public class Geofencer implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<Status> {

    private static final String TAG = Geofencer.class.getSimpleName();

    private Context mContext;
    private List<Geofence> geofenceList;
    private GoogleApiClient googleApiClient;
    private boolean geofencesAdded;

    public Geofencer() throws InstantiationException {
        throw new InstantiationException("Must supply some geofences");
    }

    public Geofencer(Context context, List<Geofence> geofenceList) {
        this.geofenceList = geofenceList;
        mContext = context;

        geofencesAdded = false;
        createGoogleApiClient();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Connected to GoogleApiClient");
        // If they failed earlier then try adding them on reconnection
        if (!geofencesAdded) {
            updateGeofences();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Disconnected from GoogleApiClient");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
        Log.i(TAG, "Connection failed: ErrorCode = " + result.getErrorCode());
    }

    @Override
    public void onResult(@NonNull Status status) {
        if (status.isSuccess()) {
            geofencesAdded = true;
            Log.i(TAG, "Geofence update success");
        } else {
            Log.i(TAG, "Geofence update failure" + status.getStatusMessage());
        }
    }

    private void createGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }

    private void updateGeofences() {
        try {
            // Remove the old ones
            List<String> fakeGeofences = Collections.singletonList(Constants.HOME_GEOFENCE_ID);
            LocationServices.GeofencingApi
                    .removeGeofences(googleApiClient, fakeGeofences)
                    .setResultCallback(this);

            // Add them one by one so each pending intent has a unique id
            for (Geofence geofence: geofenceList) {
                LocationServices.GeofencingApi
                        .addGeofences(googleApiClient, getGeofencingRequest(geofence), getGeofencePendingIntent())
                        .setResultCallback(this);
            }

        } catch (SecurityException securityException) {
            Log.e(TAG, "Invalid location permission. You need to use ACCESS_FINE_LOCATION with geofences", securityException);
        }
    }

    /**
     * Builds and returns a GeofencingRequest. Specifies the list of geofences to be monitored.
     * Also specifies how the geofence notifications are initially triggered.
     */
    private GeofencingRequest getGeofencingRequest(Geofence geofence) {
        // The INITIAL_TRIGGER_ENTER flag indicates that geofencing service should trigger a
        // GEOFENCE_TRANSITION_ENTER notification when the geofence is added and if the device
        // is already inside that geofence.
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofences(Collections.singletonList(geofence))
                .build();
    }

    /**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private PendingIntent getGeofencePendingIntent() {
        Intent intent = new Intent(mContext, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
