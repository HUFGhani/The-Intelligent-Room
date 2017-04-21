package com.project.smarthome.smarthome.Views.Home;


import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;
import java.util.List;

public interface HomeView {
    void showToast(String message);
    void showHomeLocationFragment(LatLng location);
    void showManageDevicesFragment();
    void showDeviceListFragment(String title, int viewType);
    void showUserPreferencesFragment();
    void closeNavigationDrawer();
    void askUserToSetUpTheirLocation();
    void requestPermission(String permission, int requestCode);
    void updateDeviceListFragment();
    void setUpNavigationDrawer(HashMap<String, List<String>> drawerDetail, String firstName);
    void setUpActionBar();
    void logOut();
    void showPostArrivedNotification(String timeReceived);
    void updateAutomationStatus(boolean active);
}
