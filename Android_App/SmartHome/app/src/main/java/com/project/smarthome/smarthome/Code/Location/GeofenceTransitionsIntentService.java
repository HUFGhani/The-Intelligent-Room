package com.project.smarthome.smarthome.Code.Location;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.project.smarthome.smarthome.Code.MessageQueues.CustomMqttClient;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.HouseConfiguration;
import com.project.smarthome.smarthome.Model.Preferences.UserPreference;

import java.util.List;

/**
 * Listener for geofence transition changes.
 *
 * Receives geofence transition events from Location Services in the form of an Intent containing
 * the transition type and geofence id(s) that triggered the transition. Creates a notification
 * as the output.
 */
public class GeofenceTransitionsIntentService extends IntentService {

    protected static final String TAG = "GeofenceTransitionsIS";

    public GeofenceTransitionsIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = "Geofencing Error: Code = " + geofencingEvent.getErrorCode();
            Log.e(TAG, errorMessage);
            return;
        }

        // A single event can trigger multiple geofences.
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();
        int geofenceTransitionTypeId = geofencingEvent.getGeofenceTransition();

        for (Geofence geofence : triggeringGeofences) {
            if (geofence.getRequestId().equals("home")) {
                updateWhetherTheUserIsHome(geofenceTransitionTypeId);
            } else {
                throw new UnsupportedOperationException("Unknown geofence id=" + geofence.getRequestId());
            }
        }
    }

    private void updateWhetherTheUserIsHome(int transitionType) {
        ConfigService service = new ConfigService(this);
        HouseConfiguration config = service.getHouseConfiguration();
        UserPreference userPreference = service.getUserPreference();

        String topic = String.format("%1$s/%2$s/inHouse", config.getHouseId(), userPreference.getUserId());
        String message = (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) ? "true" : "false";

        service.saveInHouseStatus(transitionType == Geofence.GEOFENCE_TRANSITION_ENTER);

        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(message);
    }

}
