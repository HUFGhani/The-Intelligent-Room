package com.project.smarthome.smarthome.Views.HomeManagement.Devices;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;
import com.project.smarthome.smarthome.R;

import java.util.List;

public class ManageDeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = ManageDeviceAdapter.class.getSimpleName();

    private List<Device> devices;

    public ManageDeviceAdapter(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ManageDevicesViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_manage_device, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final Device device = devices.get(position);
        setUpManageDevicesViewHolder(holder, device);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  devices.get(position).getDeviceType();
    }

    private class ManageDevicesViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;
        View divider;

        ManageDevicesViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            icon = (ImageView) view.findViewById(R.id.icon);
            divider = view.findViewById(R.id.divider);
        }
    }

    private void setUpManageDevicesViewHolder(RecyclerView.ViewHolder holder, Device device) {
        final ManageDevicesViewHolder manageDevicesHolder = (ManageDevicesViewHolder) holder;
        manageDevicesHolder.name.setText(device.getName());

        switch (device.getDeviceType()) {
            case DeviceBase.DEVICE_TYPE_LIGHTING : manageDevicesHolder.icon.setImageResource(R.drawable.lightbulb_on);
                break;
            case DeviceBase.DEVICE_TYPE_HEATING : manageDevicesHolder.icon.setImageResource(R.drawable.heating_on);
                break;
            case DeviceBase.DEVICE_TYPE_SENSOR : manageDevicesHolder.icon.setImageResource(R.drawable.sensor);
                break;
        }

        if (manageDevicesHolder.getAdapterPosition() == devices.size() - 1) {
            manageDevicesHolder.divider.setVisibility(View.GONE);
        } else {
            manageDevicesHolder.divider.setVisibility(View.VISIBLE);
        }
    }
}
