package com.project.smarthome.smarthome.Views.DeviceInteraction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.project.smarthome.smarthome.Code.Constants;
import com.project.smarthome.smarthome.Code.DeviceUpdates.HeatingDeviceUpdater;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;


public class TemperaturePickerDialog extends AlertDialog {

    private static final String TAG = TemperaturePickerDialog.class.getSimpleName();

    private HeatingDeviceUpdater updater;
    private HeatingDevice device;
    private NumberPicker temperaturePicker;

    public TemperaturePickerDialog(Context context, HeatingDevice device, HeatingDeviceUpdater updater) {
        super(context);
        this.device = device;
        this.updater = updater;

        RelativeLayout relativeLayout = new RelativeLayout(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);

        temperaturePicker = new NumberPicker(context);
        temperaturePicker.setMinValue(Constants.TEMPERATURE_MIN);
        temperaturePicker.setMaxValue(Constants.TEMPERATURE_MAX);
        temperaturePicker.setValue(device.getTargetTemperature());

        relativeLayout.addView(temperaturePicker, layoutParams);

        setButton(BUTTON_POSITIVE, context.getString(android.R.string.ok), onClickListener);
        setButton(BUTTON_NEGATIVE, context.getString(android.R.string.cancel), onClickListener);

        setView(relativeLayout);
    }

    private OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    device.setTargetTemperature(temperaturePicker.getValue());
                    updater.onUserHeatingRequest(device);
                    break;
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };
}
