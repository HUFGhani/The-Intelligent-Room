package com.project.smarthome.smarthome.Model.Devices;


import java.io.Serializable;

public interface Device extends Serializable {
    int getId();
    void setId(int id);
    int getDeviceType();
    String getName();
    void setName(String name);
}
