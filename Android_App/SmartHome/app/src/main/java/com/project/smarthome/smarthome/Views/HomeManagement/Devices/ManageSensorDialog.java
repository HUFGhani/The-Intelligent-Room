package com.project.smarthome.smarthome.Views.HomeManagement.Devices;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.project.smarthome.smarthome.Code.DeviceUpdates.SensorUpdateListener;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.databinding.DialogManageSensorBinding;

public class ManageSensorDialog extends DialogFragment {

    private static final String TAG = ManageSensorDialog.class.getSimpleName();

    private SensorUpdateListener listener;
    private SensorDevice sensor;

    public ManageSensorDialog() {

    }

    public static ManageSensorDialog newInstance(SensorDevice sensor) {
        ManageSensorDialog dialog = new ManageSensorDialog();
        Bundle bundle = new Bundle();
        bundle.putSerializable("sensor", sensor);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensor = (SensorDevice) getArguments().getSerializable("sensor");

        try {
            listener = (SensorUpdateListener) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement SensorUpdateListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DialogManageSensorBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_manage_sensor, null, false);
        binding.setSensor(sensor);

        binding.sensorId.setText(getActivity().getString(R.string.label_sensor_id, sensor.getId()));

        final String[] sensorFullNames = getActivity().getResources().getStringArray(R.array.sensor_full_names);
        ArrayAdapter<String> sensorMethodNamesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, sensorFullNames);
        binding.sensorNameSpinner.setAdapter(sensorMethodNamesAdapter);
        binding.sensorNameSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                setSensorName(pos);
            }
        });
        binding.sensorNameSpinner.setText(sensor.getName());

        final String[] sensorMethodTypes = getActivity().getResources().getStringArray(R.array.sensor_method_types);
        ArrayAdapter<String> sensorMethodTypesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, sensorMethodTypes);
        binding.sensorMethodTypeSpinner.setAdapter(sensorMethodTypesAdapter);
        binding.sensorMethodTypeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                sensor.setSensorMethodType(adapterView.getItemAtPosition(pos).toString());
            }
        });


        return new AlertDialog.Builder(getActivity())
                .setView(binding.getRoot())
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onSensorUpdated(sensor);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .create();
    }

    private void setSensorName(int spinnerPos) {
        final String[] sensorRealNames = getActivity().getResources().getStringArray(R.array.sensor_real_names);
        sensor.setName(sensorRealNames[spinnerPos]);
    }
}
