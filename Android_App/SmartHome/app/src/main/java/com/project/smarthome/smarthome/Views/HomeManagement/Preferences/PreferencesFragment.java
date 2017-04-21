package com.project.smarthome.smarthome.Views.HomeManagement.Preferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.project.smarthome.smarthome.Code.DeviceUpdates.HeatingDeviceUpdater;
import com.project.smarthome.smarthome.Code.DeviceUpdates.LightingDeviceUpdater;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Presenters.HomeManagement.Preferences.PreferencesPresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Utilities.TextUtilities;
import com.project.smarthome.smarthome.Views.DeviceInteraction.ColourPicker.ColourPickerDialog;
import com.project.smarthome.smarthome.Views.DeviceInteraction.DeviceCardList.DeviceCardListFragment;
import com.project.smarthome.smarthome.Views.DeviceInteraction.TemperaturePickerDialog;
import com.project.smarthome.smarthome.databinding.FragmentPreferencesBinding;

import static com.project.smarthome.smarthome.Presenters.DeviceInteraction.DeviceCardListPresenter.VIEW_ALL_DEVICES;

/**
 * Use the {@link PreferencesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreferencesFragment extends Fragment implements PreferencesView, HeatingDeviceUpdater, LightingDeviceUpdater {

    private FragmentPreferencesBinding binding;
    private PreferencesPresenter presenter;

    public PreferencesFragment() {
        // Required empty public constructor
    }

    public static PreferencesFragment newInstance() {
        return new PreferencesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PreferencesPresenter(this, new ConfigService(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPreferencesBinding.inflate(inflater, container, false);

        String[] heatingAutomationTypes = getResources().getStringArray(R.array.heating_automation_types);
        ArrayAdapter<String> heatingAutomationTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, heatingAutomationTypes);
        binding.heatingActionMethod.setAdapter(heatingAutomationTypeAdapter);

        String[] lightingAutomationTypes = getResources().getStringArray(R.array.lighting_automation_types);
        ArrayAdapter<String> lightingAutomationTypeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, lightingAutomationTypes);
        binding.lightingActionMethod.setAdapter(lightingAutomationTypeAdapter);

        binding.heatingActionMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                presenter.onHeatingAutomationTypeChanged(pos);
            }
        });

        binding.lightingActionMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                presenter.onLightingAutomationTypeChanged(pos);
            }
        });

        binding.synchronize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onUserPressedSave();
            }
        });

        binding.targetTemperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTemperaturePickerDialog();
            }
        });

        binding.colourPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.chooseNewColourSettings();
            }
        });

        presenter.onStart();

        return binding.getRoot();
    }

    @Override
    public void setTitle() {
        getActivity().setTitle(getString(R.string.manage_preferences));
    }

    @Override
    public void showSuccessMessage() {
        showToast(getString(R.string.saved));
    }

    @Override
    public void returnToAllDevicesFragment() {
        DeviceCardListFragment fragment = DeviceCardListFragment.newInstance(getString(R.string.all_devices), VIEW_ALL_DEVICES);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment_placeholder, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    @Override
    public void setTemperature(int temperature) {
        binding.targetTemperature.setText(String.valueOf(temperature));
    }

    @Override
    public void setLightColour(int colour) {
        binding.colourPreview.setBackgroundColor(colour);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void showTemperaturePickerDialog() {
        HeatingDevice temporaryDevice = HeatingDevice.getDefault();
        temporaryDevice.setTargetTemperature(Integer.valueOf(binding.targetTemperature.getText().toString()));
        new TemperaturePickerDialog(getContext(), temporaryDevice, this).show();
    }

    @Override
    public void showColourPickerDialog(LightingDevice light) {
        new ColourPickerDialog(getContext(), light.getColour(), this, light).show();
    }

    @Override
    public void setHeatingAutomationType(String type) {
        binding.heatingActionMethod.setText(TextUtilities.capitaliseFirstLetter(type));
    }

    @Override
    public void setLightingAutomationType(String type) {
        binding.lightingActionMethod.setText(TextUtilities.capitaliseFirstLetter(type));
    }

    @Override
    public void onUserHeatingRequest(HeatingDevice device) {
        presenter.onUserChoseNewTemperature(device.getTargetTemperature());
    }

    @Override
    public void onUserLightingRequest(LightingDevice device) {
        presenter.onUserChoseNewLighting(device);
    }
}
