package com.project.smarthome.smarthome.Views.RecyclerViews;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.smarthome.smarthome.Code.DeviceUpdates.HeatingDeviceUpdater;
import com.project.smarthome.smarthome.Code.DeviceUpdates.LightingDeviceUpdater;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.DeviceInteraction.ColourPicker.ColourPickerDialog;
import com.project.smarthome.smarthome.Views.DeviceInteraction.TemperaturePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements LightingDeviceUpdater, HeatingDeviceUpdater {

    private static final String TAG = DeviceAdapter.class.getSimpleName();

    private Activity mContext;
    private LightingDeviceUpdater parentLightingUpdateListener;
    private HeatingDeviceUpdater parentHeatingUpdateListener;
    private List<Device> devices;

    public DeviceAdapter(Activity context, LightingDeviceUpdater parentLightingUpdateListener, HeatingDeviceUpdater parentHeatingUpdateListener, List<Device> devices) {
        this.mContext = context;
        this.parentLightingUpdateListener = parentLightingUpdateListener;
        this.parentHeatingUpdateListener = parentHeatingUpdateListener;
        this.devices = devices;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DeviceBase.DEVICE_TYPE_HEATING : return new HeatingViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.card_device_heating, parent, false));
            case DeviceBase.DEVICE_TYPE_LIGHTING : return new LightingViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.card_device_lighting, parent, false));
            case DeviceBase.DEVICE_TYPE_SENSOR : return new SensorViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.card_device_sensor, parent, false));
        }
        throw new UnsupportedOperationException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Device device = devices.get(position);

        switch (holder.getItemViewType()) {
            case DeviceBase.DEVICE_TYPE_HEATING : setUpHeatingViewHolder(holder, device);
                break;
            case DeviceBase.DEVICE_TYPE_LIGHTING : setUpLightingViewHolder(holder, device);
                break;
            case DeviceBase.DEVICE_TYPE_SENSOR : setUpSensorViewHolder(holder, device);
                break;
            default : throw new UnsupportedOperationException("Invalid view type");
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  devices.get(position).getDeviceType();
    }

    @Override
    public void onUserLightingRequest(LightingDevice device) {
        Log.i(TAG, "onUserLightingRequest");
        updateListOfDevices(device);
        parentLightingUpdateListener.onUserLightingRequest(device);
    }

    @Override
    public void onUserHeatingRequest(HeatingDevice device) {
        Log.i(TAG, "onUserLightingRequest");
        updateListOfDevices(device);
        parentHeatingUpdateListener.onUserHeatingRequest(device);
    }

    private void updateListOfDevices(Device device) {
        for (int index = 0; index < devices.size(); index++) {
            if (devices.get(index).getId() == device.getId() && devices.get(index).getDeviceType() == device.getDeviceType()) {
                devices.set(index, device);
                break;
            }
        }
    }

    private class LightingViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView lightbulbOn, lightbulbOff;
        View colourPreview;

        LightingViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            lightbulbOn = (ImageView) view.findViewById(R.id.lightbulb_on);
            lightbulbOff = (ImageView) view.findViewById(R.id.lightbulb_off);
            colourPreview = view.findViewById(R.id.colour_preview);
        }
    }

    private class HeatingViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView currentTemperature, targetTemperature;

        HeatingViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            currentTemperature = (TextView) view.findViewById(R.id.currentTemperature);
            targetTemperature = (TextView) view.findViewById(R.id.targetTemperature);
        }
    }

    private class SensorViewHolder extends RecyclerView.ViewHolder {
        TextView title, lastUpdated, sensorValue;

        SensorViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            lastUpdated = (TextView) view.findViewById(R.id.last_updated);
            sensorValue = (TextView) view.findViewById(R.id.sensor_value);
        }
    }

    private void setUpHeatingViewHolder(RecyclerView.ViewHolder holder, Device device) {
        final HeatingDeviceUpdater heatingDeviceUpdater = this;
        final HeatingDevice heatingDevice = (HeatingDevice) device;
        final HeatingViewHolder heatingHolder = (HeatingViewHolder) holder;

        heatingHolder.title.setText(device.getName());
        heatingHolder.currentTemperature.setText(mContext.getString(R.string.degrees_celsius, heatingDevice.getCurrentTemperature()));
        heatingHolder.targetTemperature.setText(mContext.getString(R.string.degrees_celsius, heatingDevice.getTargetTemperature()));

        heatingHolder.targetTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TemperaturePickerDialog(mContext, heatingDevice, heatingDeviceUpdater).show();
            }
        });
    }

    private void setUpLightingViewHolder(RecyclerView.ViewHolder holder, final Device device) {
        final LightingDeviceUpdater lightingDeviceUpdater = this;
        final LightingDevice lightingDevice = (LightingDevice) device;
        final LightingViewHolder lightingHolder = (LightingViewHolder) holder;

        lightingHolder.title.setText(device.getName());

        lightingHolder.lightbulbOn.setVisibility( (lightingDevice.isActive()) ? View.VISIBLE : View.GONE);
        lightingHolder.lightbulbOff.setVisibility( (lightingDevice.isActive()) ? View.GONE : View.VISIBLE);

        lightingHolder.colourPreview.setBackgroundColor(lightingDevice.getColour());

        lightingHolder.colourPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ColourPickerDialog(mContext, lightingDevice.getColour(), lightingDeviceUpdater, lightingDevice).show();
            }
        });
        lightingHolder.lightbulbOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightingDevice.setActive(false);
                onUserLightingRequest(lightingDevice);
            }
        });
        lightingHolder.lightbulbOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lightingDevice.setActive(true);
                onUserLightingRequest(lightingDevice);
            }
        });
    }

    private void setUpSensorViewHolder(RecyclerView.ViewHolder holder, Device device) {
        final SensorViewHolder sensorHolder = (SensorViewHolder) holder;
        SensorDevice sensorDevice = (SensorDevice) device;

        sensorHolder.title.setText(sensorDevice.getName());
        sensorHolder.sensorValue.setText(mContext.getString(R.string.value, String.valueOf(((SensorDevice) device).getSensorValue())));

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        sensorHolder.lastUpdated.setText(mContext.getString(R.string.last_updated, dateFormat.format(sensorDevice.getLastUpdated())));
    }
}
