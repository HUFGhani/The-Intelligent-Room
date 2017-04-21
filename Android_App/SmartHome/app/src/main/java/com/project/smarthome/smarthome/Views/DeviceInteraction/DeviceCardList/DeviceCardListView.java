package com.project.smarthome.smarthome.Views.DeviceInteraction.DeviceCardList;


import com.project.smarthome.smarthome.Model.Devices.Device;

import java.util.List;

public interface DeviceCardListView {
    void setTitle(String title);
    void updateView(List<Device> devices);
}
