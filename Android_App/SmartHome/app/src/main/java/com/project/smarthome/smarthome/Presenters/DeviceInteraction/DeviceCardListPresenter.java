package com.project.smarthome.smarthome.Presenters.DeviceInteraction;


import android.content.Context;

import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.DeviceInteraction.DeviceCardList.DeviceCardListView;

import java.util.ArrayList;
import java.util.List;

public class DeviceCardListPresenter {
    public static final int VIEW_ALL_DEVICES = 1;
    public static final int VIEW_CATEGORY = 2;

    private DeviceCardListView view;
    private Context context;
    private ConfigService service;
    private String title;
    private int viewType;
    private boolean visible;

    public DeviceCardListPresenter(DeviceCardListView view, Context context, ConfigService configService, String title, int viewType) {
        this.view = view;
        this.context = context;
        this.service = configService;
        this.title = title;
        this.viewType = viewType;
        this.visible = true;
    }

    public void onStart() {
        view.setTitle(title);
        updateView();
    }

    public void visibilityChanged(boolean visible) {
        this.visible = visible;
    }

    public void requestUpdate() {
        updateView();
    }

    private void updateView() {
        if (!visible) {
            return;
        }

        List<Device> devices = new ArrayList<>();
        LightingDevice lights = null;
        HeatingDevice heatingDevice = null;

        switch (viewType) {
            case VIEW_ALL_DEVICES:
                heatingDevice = service.getHeatingDevice();
                lights = service.getLightingDevice();
                break;
            case VIEW_CATEGORY:
                if (title.equals(context.getString(R.string.category_lighting))) {
                    lights = service.getLightingDevice();
                    break;
                } else if (title.equals(context.getString(R.string.category_heating))) {
                    heatingDevice = service.getHeatingDevice();
                    break;
                }
        }

        if (heatingDevice != null) {
            devices.add(heatingDevice);
        }
        if (lights != null) {
            devices.add(lights);
        }
        view.updateView(devices);
    }
}
