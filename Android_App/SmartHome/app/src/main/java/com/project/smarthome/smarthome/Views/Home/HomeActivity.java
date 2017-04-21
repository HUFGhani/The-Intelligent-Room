package com.project.smarthome.smarthome.Views.Home;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.project.smarthome.smarthome.Code.DeviceUpdates.HeatingDeviceUpdater;
import com.project.smarthome.smarthome.Code.DeviceUpdates.LightingDeviceUpdater;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Model.Location.LocationSelectedListener;
import com.project.smarthome.smarthome.Model.Location.OpenLocationChooserChoiceListener;
import com.project.smarthome.smarthome.Presenters.Home.HomePresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Utilities.NumberUtilities;
import com.project.smarthome.smarthome.Views.DeviceInteraction.DeviceCardList.DeviceCardListFragment;
import com.project.smarthome.smarthome.Views.HomeManagement.Devices.ManageDevicesFragment;
import com.project.smarthome.smarthome.Views.HomeManagement.Location.LocationChooserFragment;
import com.project.smarthome.smarthome.Views.HomeManagement.Location.NoLocationSetUpDialog;
import com.project.smarthome.smarthome.Views.HomeManagement.Preferences.PreferencesFragment;
import com.project.smarthome.smarthome.Views.LogInRegister.Shared.LogInRegisterActivity;
import com.project.smarthome.smarthome.Views.RecyclerViews.NavigationDrawerExpandableListAdapter;
import com.project.smarthome.smarthome.databinding.ActivityHomeBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements HeatingDeviceUpdater, LightingDeviceUpdater, ManageDevicesFragment.ManageDevicesUpdateListener, LocationSelectedListener, OpenLocationChooserChoiceListener, HomeView {

    private static final String TAG = HomeActivity.class.getSimpleName();

    private ActivityHomeBinding binding;
    private HomePresenter presenter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        binding.switchAutomationStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean active) {
                presenter.onAutomationStatusChanged(active);
            }
        });

        presenter = new HomePresenter(this, this, new ConfigService(this));
        presenter.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Changes whether the toggle icon is a hamburger or an arrow
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check if the drawer toggle can handle this item
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle any other action bar items here
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserHeatingRequest(HeatingDevice heating) {
        presenter.onUserUpdatedHeating(heating);
    }

    @Override
    public void onUserLightingRequest(LightingDevice light) {
        presenter.onUserUpdatedLighting(light);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        presenter.onPermissionReceived(requestCode, grantResults[0]);
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(this, LogInRegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void showPostArrivedNotification(String timeReceived) {
        showNotification(HomeActivity.class, "Post", "Arrived " + timeReceived);
    }

    @Override
    public void updateAutomationStatus(boolean active) {
        binding.switchAutomationStatus.setChecked(active);
    }

    @Override
    public void setUpNavigationDrawer(final HashMap<String, List<String>> drawerDetail, String firstName) {
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.drawer_open, R.string.close_drawer);
        binding.drawerLayout.addDrawerListener(drawerToggle);

        final List<String> expandableListHeadings = new ArrayList<>(drawerDetail.keySet());
        ExpandableListAdapter expandableListAdapter = new NavigationDrawerExpandableListAdapter(this, drawerDetail);
        binding.navDrawerExpandableListView.setAdapter(expandableListAdapter);

        binding.navDrawerExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String headingType = expandableListHeadings.get(groupPosition);
                String clickedItem = drawerDetail.get(expandableListHeadings.get(groupPosition)).get(childPosition);
                presenter.onNavDrawerItemClicked(headingType, clickedItem);
                return false;
            }
        });

        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.logOut();
            }
        });

        binding.firstName.setText(firstName);
    }

    @Override
    public void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_placeholder, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void showDeviceListFragment(String title, int viewType) {
        DeviceCardListFragment fragment = DeviceCardListFragment.newInstance(title, viewType);
        showFragment(fragment);
    }

    @Override
    public void showUserPreferencesFragment() {
        showFragment(PreferencesFragment.newInstance());
    }

    @Override
    public void showManageDevicesFragment() {
        showFragment(ManageDevicesFragment.newInstance());
    }

    @Override
    public void showHomeLocationFragment(LatLng location) {
        LocationChooserFragment fragment = LocationChooserFragment.newInstance(location);
        showFragment(fragment);
    }

    @Override
    public void closeNavigationDrawer() {
        binding.drawerLayout.closeDrawers();
    }

    @Override
    public void askUserToSetUpTheirLocation() {
        new NoLocationSetUpDialog().show(getSupportFragmentManager(), "NoLocationSetUpDialog");
    }

    @Override
    public void requestPermission(String permission, int requestCode) {
        ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
    }

    @Override
    public void updateDeviceListFragment() {
        // TODO - Check if the currently displayed fragment is actually a DeviceCardListFragment
        DeviceCardListFragment fragment = (DeviceCardListFragment) getSupportFragmentManager().findFragmentById(R.id.home_fragment_placeholder);
        fragment.requestUpdate();
    }

    @Override
    public void onLocationSelected(LatLng location) {
        presenter.onUserPickedNewHomeLocation(location);
    }

    @Override
    public void onDevicesUpdated(ArrayList<Device> devices) {
        presenter.onUserFinishedManagingDevices(devices);
    }

    @Override
    public void onUserSelectedOpenLocationChooser() {
        presenter.onUserReadyToChangeHomeLocation();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
        else {
            getFragmentManager().popBackStack();
        }
    }

    private void showNotification(Class intendedActivity, String title, String message) {
        // Create an explicit content Intent that starts the chosen Activity.
        Intent notificationIntent = new Intent(getApplicationContext(), intendedActivity);

        // Construct a task stack.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(HomeActivity.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 7
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        // Define the notification settings.
        builder
                .setSmallIcon(R.drawable.logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                .setColor(Color.TRANSPARENT)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user opens it.
        builder.setAutoCancel(true);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(NumberUtilities.generateNonSecureRandom(), builder.build());
    }
}
