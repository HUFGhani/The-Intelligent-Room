package com.project.smarthome.smarthome.Presenters.Home;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.maps.model.LatLng;
import com.project.smarthome.smarthome.Code.Constants;
import com.project.smarthome.smarthome.Code.Location.Geofencer;
import com.project.smarthome.smarthome.Code.MessageQueues.CustomMqttClient;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.HomeConfiguration.HouseConfigurationSubscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.HomeConfiguration.HouseConfigurationSubscriptionListener;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Hue.HueSubscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Hue.HueSubscriptionListener;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Nest.NestSubscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Nest.NestSubscriptionListener;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Sensors.SensorSubscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Sensors.SensorSubscriptionListener;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Subscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.UserPreference.UserPreferenceSubscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.UserPreference.UserPreferenceSubscriptionListener;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.PhilipsHue;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;
import com.project.smarthome.smarthome.Model.HouseConfiguration;
import com.project.smarthome.smarthome.Model.Preferences.UserPreference;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.Home.HomeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.project.smarthome.smarthome.Presenters.DeviceInteraction.DeviceCardListPresenter.VIEW_ALL_DEVICES;
import static com.project.smarthome.smarthome.Presenters.DeviceInteraction.DeviceCardListPresenter.VIEW_CATEGORY;

public class HomePresenter implements HueSubscriptionListener, NestSubscriptionListener, SensorSubscriptionListener, HouseConfigurationSubscriptionListener, UserPreferenceSubscriptionListener {

    private static final String TAG = HomePresenter.class.getSimpleName();
    private static final int PERMISSION_REQUEST_LOCATION_FOR_GEOFENCE = 201;
    private static final int PERMISSION_REQUEST_LOCATION_FOR_HOME_LOCATION_SETUP = 202;

    private HomeView view;
    private Context context;
    private ConfigService configService;

    private HouseConfiguration houseConfiguration;
    private UserPreference userPreference;
    private List<Subscriber> queueSubscribers;

    public HomePresenter(HomeView view, Context context, ConfigService configService) {
        this.view = view;
        this.context = context;
        this.configService = configService;

        houseConfiguration = this.configService.getHouseConfiguration();
        userPreference = this.configService.getUserPreference();
        if (houseConfiguration == null || userPreference == null) {
            view.showToast(context.getString(R.string.error_failure_getting_saved_configs));
            logOut();
        }

        setUpQueueSubscribers();
    }

    @Override
    public void onHueMessageReceived(LightingDevice device) {
        configService.saveLightingDevice(device);
        view.updateDeviceListFragment();
        onAutomationStatusChanged(device.isAutomated());
    }

    @Override
    public void onNestMessageReceived(HeatingDevice device) {
        configService.saveHeatingDevice(device);
        view.updateDeviceListFragment();
        onAutomationStatusChanged(device.isAutomated());
    }

    @Override
    public void onSensorMessageReceived(SensorDevice sensor) {
        houseConfiguration.updateSensor(sensor);
        saveHouseConfiguration(false);

        if (sensor.getSensorType() == SensorDevice.SENSOR_TYPE_TOUCH) {
            if (configService.getLastReceivedPostNotificationTime() < sensor.getLastUpdated()) {
                String timeReceived = new SimpleDateFormat("HH:mm:ss").format(sensor.getLastUpdated());
                view.showPostArrivedNotification(timeReceived);
                configService.saveLastReceivedPostNotificationTime(sensor.getLastUpdated());
            }
        }
    }

    @Override
    public void onHouseConfigurationMessageReceived(HouseConfiguration houseConfiguration) {
        this.houseConfiguration = houseConfiguration;
        saveHouseConfiguration(false);
    }

    @Override
    public void onUserPreferenceMessageReceived(UserPreference userPreference) {
        this.userPreference = userPreference;
        configService.saveUserPreferences(userPreference);
    }

    public void onStart() {
        startQueueSubscribers();
        view.setUpActionBar();
        view.setUpNavigationDrawer(getNavigationDrawerListDetail(), userPreference.getFirstName());
        showDeviceListFragment(VIEW_ALL_DEVICES, context.getString(R.string.all_devices));
        if (houseConfiguration.getLocation() == null) {
            onUserHasNoLocationSetUp();
        } else {
            setUpGeoFence();
        }
    }

    public void onStop() {
        stopQueueSubscribers();
    }

    public void onPermissionReceived(int requestCode, int result) {
        if (requestCode == PERMISSION_REQUEST_LOCATION_FOR_GEOFENCE) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                setUpGeoFence();
            } else {
                view.showToast(context.getString(R.string.geofencing_explanation));
            }
        }

        if (requestCode == PERMISSION_REQUEST_LOCATION_FOR_HOME_LOCATION_SETUP) {
            if (result == PackageManager.PERMISSION_GRANTED) {
                showHomeLocationFragment();
            } else {
                view.showToast(context.getString(R.string.geofencing_explanation));
            }
        }
    }

    public void onNavDrawerItemClicked(String headingType, String clickedItem) {
        if (headingType.equals(context.getString(R.string.heading_devices))) {
            if (clickedItem.equals(context.getString(R.string.all_devices))) {
                showDeviceListFragment(VIEW_ALL_DEVICES, clickedItem);
            } else {
                showDeviceListFragment(VIEW_CATEGORY, clickedItem);
            }
        } else if (headingType.equals(context.getString(R.string.heading_home_management))) {
            if (clickedItem.equals(context.getString(R.string.manage_devices))) {
                showManageDevicesFragment();
            } else if (clickedItem.equals(context.getString(R.string.manage_location))) {
                showHomeLocationFragment();
            } else if (clickedItem.equals(context.getString(R.string.manage_preferences))) {
                showUserPreferencesFragment();
            }
        }
        view.closeNavigationDrawer();
    }

    public void logOut() {
        configService.deleteAllConfig();
        view.logOut();
    }

    private void showManageDevicesFragment() {
        view.showManageDevicesFragment();
    }

    private void showDeviceListFragment(int viewType, String title) {
        view.showDeviceListFragment(title, viewType);
    }

    private void showUserPreferencesFragment() {
        view.showUserPreferencesFragment();
    }

    private void onUserHasNoLocationSetUp() {
        view.askUserToSetUpTheirLocation();
    }

    public void onUserReadyToChangeHomeLocation() {
        showHomeLocationFragment();
    }

    private void showHomeLocationFragment() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            view.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_REQUEST_LOCATION_FOR_HOME_LOCATION_SETUP);
            return;
        }
        view.showHomeLocationFragment(houseConfiguration.getLocation());
    }

    public void onUserPickedNewHomeLocation(LatLng location) {
        // Return to the home screen
        showDeviceListFragment(VIEW_ALL_DEVICES, context.getString(R.string.all_devices));
        view.showToast(context.getString(R.string.home_location_updated));

        // Save new configuration
        houseConfiguration.setLocation(location);
        saveHouseConfiguration(true);

        // Update geofence
        setUpGeoFence();
    }

    private void setUpQueueSubscribers() {
        queueSubscribers = new ArrayList<>();
        queueSubscribers.add(new NestSubscriber(this, houseConfiguration.getHouseId()));
        queueSubscribers.add(new HueSubscriber(this, houseConfiguration.getHouseId()));
        queueSubscribers.add(new HouseConfigurationSubscriber(this, houseConfiguration.getHouseId()));
        queueSubscribers.add(new UserPreferenceSubscriber(this, houseConfiguration.getHouseId(), userPreference.getUserId()));
        for (SensorDevice sensorDevice : houseConfiguration.getSensors()) {
            if (sensorDevice.getSensorType() == SensorDevice.SENSOR_TYPE_TOUCH) {
                queueSubscribers.add(new SensorSubscriber(this, houseConfiguration.getHouseId(), sensorDevice.getId()));
            }
        }
    }

    private void saveHouseConfiguration(boolean publish) {
        configService.saveHouseConfiguration(houseConfiguration);
        if (publish) {
            publishHouseConfiguration();
        }
    }

    private void startQueueSubscribers() {
        for (Subscriber queue : queueSubscribers) {
            queue.subscribe();
        }
    }

    private void stopQueueSubscribers() {
        for (Subscriber queue : queueSubscribers) {
            queue.unsubscribe();
        }
    }

    private void publishHouseConfiguration() {
        String topic = String.format("%1$s/configuration", houseConfiguration.getHouseId());
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(houseConfiguration.toString());
    }

    public void onUserUpdatedLighting(LightingDevice light) {
        publishLighting(light);
        configService.saveLightingDevice(light);
    }

    public void onUserUpdatedHeating(HeatingDevice heating) {
        publishHeating(heating);
        configService.saveHeatingDevice(heating);
    }

    private void publishLighting(LightingDevice light) {
        String message = new PhilipsHue(light).toString();
        String topic = String.format("%1$s/actuator/hue", houseConfiguration.getHouseId());
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(message);
    }

    private void publishHeating(HeatingDevice heating) {
        String message = heating.toString();
        String topic = String.format("%1$s/actuator/nest", houseConfiguration.getHouseId());
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(message);
    }

    public void onAutomationStatusChanged(boolean active) {
        HeatingDevice heating = configService.getHeatingDevice();
        if (heating != null) {
            heating.setAutomated(active);
            configService.saveHeatingDevice(heating);
            publishHeating(heating);
        }

        LightingDevice light = configService.getLightingDevice();
        if (light != null) {
            light.setAutomated(active);
            configService.saveLightingDevice(light);
            publishLighting(light);
        }

        if (active) {
            publishToInHouse();
        }

        view.updateAutomationStatus(active);
    }

    public void onUserFinishedManagingDevices(List<Device> devices) {
        stopQueueSubscribers();

        ArrayList<SensorDevice> sensors = new ArrayList<>();
        for (Device device : devices) {

            switch (device.getDeviceType()) {
                case DeviceBase.DEVICE_TYPE_HEATING:
                    configService.saveHeatingDevice((HeatingDevice) device);
                    publishHeating((HeatingDevice) device);
                    break;
                case DeviceBase.DEVICE_TYPE_LIGHTING:
                    configService.saveLightingDevice((LightingDevice) device);
                    publishLighting((LightingDevice) device);
                    break;
                case DeviceBase.DEVICE_TYPE_SENSOR:
                    sensors.add((SensorDevice) device);
                    break;
            }
        }
        houseConfiguration.setSensors(sensors);
        saveHouseConfiguration(true);

        setUpQueueSubscribers();
        startQueueSubscribers();
    }

    private HashMap<String, List<String>> getNavigationDrawerListDetail() {
        HashMap<String, List<String>> listDetail = new HashMap<>();

        List<String> categories = new ArrayList<>();
        categories.add(context.getString(R.string.all_devices));
        categories.add(context.getString(R.string.category_heating));
        categories.add(context.getString(R.string.category_lighting));

        List<String> homeManagement = new ArrayList<>();
        homeManagement.add(context.getString(R.string.manage_devices));
        homeManagement.add(context.getString(R.string.manage_location));
        homeManagement.add(context.getString(R.string.manage_preferences));

        listDetail.put(context.getString(R.string.heading_devices), categories);
        listDetail.put(context.getString(R.string.heading_home_management), homeManagement);
        return listDetail;
    }

    private void setUpGeoFence() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            view.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, PERMISSION_REQUEST_LOCATION_FOR_GEOFENCE);
            return;
        }

        final int GEOFENCE_DEFAULT_EXPIRATION_MS = 1000 * 60 * 60 * 24 * 14; // 2 weeks
        final int GEOFENCE_RADIUS_M = 100;

        Geofence.Builder builder = new Geofence.Builder()
                .setExpirationDuration(GEOFENCE_DEFAULT_EXPIRATION_MS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT);

        List<Geofence> geofences = new ArrayList<>();

        geofences.add(builder
                .setRequestId(Constants.HOME_GEOFENCE_ID)
                .setCircularRegion(houseConfiguration.getLocation().latitude, houseConfiguration.getLocation().latitude, GEOFENCE_RADIUS_M)
                .build());

        Geofencer geofencer = new Geofencer(context, geofences);
    }

    private void publishToInHouse() {
        HouseConfiguration config = configService.getHouseConfiguration();
        UserPreference userPreference = configService.getUserPreference();

        String topic = String.format("%1$s/%2$s/inHouse", config.getHouseId(), userPreference.getUserId());
        String inHouse = String.valueOf(configService.getInHouseStatus());

        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(inHouse);
    }
}
