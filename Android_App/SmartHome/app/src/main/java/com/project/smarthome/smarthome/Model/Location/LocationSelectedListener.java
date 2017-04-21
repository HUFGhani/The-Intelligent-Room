package com.project.smarthome.smarthome.Model.Location;


import com.google.android.gms.maps.model.LatLng;

public interface LocationSelectedListener {
    void onLocationSelected(LatLng location);
}
