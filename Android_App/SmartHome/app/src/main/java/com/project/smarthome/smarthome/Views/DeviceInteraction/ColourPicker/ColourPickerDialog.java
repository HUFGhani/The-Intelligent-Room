package com.project.smarthome.smarthome.Views.DeviceInteraction.ColourPicker;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;

import com.project.smarthome.smarthome.Code.DeviceUpdates.LightingDeviceUpdater;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.R;

public class ColourPickerDialog extends AlertDialog {

    private ColourPicker colorPickerView;
    private final LightingDeviceUpdater onColourSelectedListener;
    private LightingDevice device;

    private SeekBar brightnessSlider;

    public ColourPickerDialog(Context context, int initialColor, LightingDeviceUpdater onColorSelectedListener, LightingDevice device) {
        super(context);

        this.onColourSelectedListener = onColorSelectedListener;
        this.device = device;

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        colorPickerView = new ColourPicker(context);
        colorPickerView.setColor(initialColor);
        linearLayout.addView(colorPickerView, layoutParams);

        TextView brightnessLabel = new TextView(context);
        brightnessLabel.setText(getContext().getString(R.string.brightness));
        linearLayout.addView(brightnessLabel);

        brightnessSlider = new SeekBar(context);
        brightnessSlider.setMax(255);
        brightnessSlider.setProgress(device.getBrightness());
        linearLayout.addView(brightnessSlider);

        setButton(BUTTON_POSITIVE, context.getString(android.R.string.ok), onClickListener);
        setButton(BUTTON_NEGATIVE, context.getString(android.R.string.cancel), onClickListener);

        setView(linearLayout);
    }

    private OnClickListener onClickListener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    device.setColour(colorPickerView.getColor());
                    device.setBrightness(brightnessSlider.getProgress());
                    device.setSaturation(colorPickerView.getSaturation());
                    onColourSelectedListener.onUserLightingRequest(device);
                    break;
                case BUTTON_NEGATIVE:
                    dialog.dismiss();
                    break;
            }
        }
    };

}
