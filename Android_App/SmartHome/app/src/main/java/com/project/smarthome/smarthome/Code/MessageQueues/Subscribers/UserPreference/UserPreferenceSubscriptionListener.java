package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.UserPreference;


import com.project.smarthome.smarthome.Model.Preferences.UserPreference;

public interface UserPreferenceSubscriptionListener {
    void onUserPreferenceMessageReceived(UserPreference userPreference);
}
