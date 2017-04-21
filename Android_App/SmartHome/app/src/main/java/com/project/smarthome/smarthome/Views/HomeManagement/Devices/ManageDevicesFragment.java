package com.project.smarthome.smarthome.Views.HomeManagement.Devices;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.smarthome.smarthome.Code.DeviceUpdates.SensorUpdateListener;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.databinding.FragmentManageDevicesBinding;

import java.util.ArrayList;

/**
 * Activities that contain this fragment must implement the
 * {@link ManageDevicesUpdateListener} interface
 * to handle interaction events.
 * Use the {@link ManageDevicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageDevicesFragment extends Fragment implements SensorUpdateListener {

    private static final String TAG = ManageDevicesFragment.class.getSimpleName();

    private FragmentManageDevicesBinding binding;
    private ArrayList<Device> devices;
    private ManageDeviceAdapter adapter;
    private ManageDevicesUpdateListener updateListener;
    private ConfigService service;

    private Fragment thisFragment;

    public ManageDevicesFragment() {
        // Required empty public constructor
    }

    public static ManageDevicesFragment newInstance() {
        return new ManageDevicesFragment();
    }

    public interface ManageDevicesUpdateListener {
        void onDevicesUpdated(ArrayList<Device> devices);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.manage_devices));
        devices = new ArrayList<>();

        service = new ConfigService(getContext());
        devices.addAll(service.getAllDevices());
        adapter = new ManageDeviceAdapter(devices);

        thisFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentManageDevicesBinding.inflate(inflater, container, false);



        binding.addSensorDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SensorDevice creationSensor = new SensorDevice();
                creationSensor.setName("motionSensor");
                final ManageSensorDialog dialog = ManageSensorDialog.newInstance(creationSensor);
                dialog.setTargetFragment(thisFragment, 1);
                dialog.show(ManageDevicesFragment.this.getChildFragmentManager(), "dialog_fragment");
            }
        });

        binding.addHeatingDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListOfDevices(HeatingDevice.getDefault());
                setVisibilityOfAddHeatingButton();
            }
        });

        binding.addLightingDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListOfDevices(LightingDevice.getDefault());
                setVisibilityOfAddLightingButton();
            }
        });

        binding.manageDevicesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.manageDevicesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.manageDevicesRecyclerView.setAdapter(adapter);

        setVisibilityOfAddHeatingButton();
        setVisibilityOfAddLightingButton();

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ManageDevicesUpdateListener) {
            updateListener = (ManageDevicesUpdateListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ManageDevicesUpdateListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        updateListener = null;
    }

    @Override
    public void onSensorUpdated(SensorDevice sensor) {
        updateListOfDevices(sensor);
    }

    private void updateListOfDevices(Device device) {
        if (device.getId() == 0) {
            // New device, add it to the list
            if (device.getDeviceType() == DeviceBase.DEVICE_TYPE_SENSOR) {
                device.setId(generateNewIdForSensor());
            }
            devices.add(device);
            adapter.notifyItemInserted(devices.size() - 1);
        } else {
            // Device has just been updated so update the list
            for (int index = 0; index < devices.size(); index++) {
                if (devices.get(index).getId() == device.getId() && devices.get(index).getDeviceType() == device.getDeviceType()) {
                    devices.set(index, device);
                    adapter.notifyItemChanged(index);
                }
            }
        }
        updateListener.onDevicesUpdated(devices);
    }

    private int generateNewIdForSensor() {
        int maxId = 0;
        for (Device device : devices) {
            if (device.getDeviceType() == DeviceBase.DEVICE_TYPE_SENSOR) {
                if (device.getId() > maxId) {
                    maxId = device.getId();
                }
            }
        }
        return maxId + 1;
    }

    private void setVisibilityOfAddHeatingButton() {
        binding.addHeatingDevice.setVisibility((service.getHeatingDevice() == null) ? View.VISIBLE : View.GONE);
    }

    private void setVisibilityOfAddLightingButton() {
        binding.addLightingDevice.setVisibility((service.getLightingDevice() == null) ? View.VISIBLE : View.GONE);
    }
}
