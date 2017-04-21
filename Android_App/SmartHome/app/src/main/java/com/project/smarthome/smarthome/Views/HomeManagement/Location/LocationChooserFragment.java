package com.project.smarthome.smarthome.Views.HomeManagement.Location;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.project.smarthome.smarthome.Code.Location.Locator;
import com.project.smarthome.smarthome.Model.Location.LocationSelectedListener;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.databinding.FragmentLocationChooserBinding;

/**
 * Use the {@link LocationChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LocationChooserFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = LocationChooserFragment.class.getSimpleName();
    private Marker locationMarker;
    private Locator locator;
    private LocationSelectedListener locationSelectedListener;
    private LatLng startUpLocation;

    private static final LatLng DEFAULT_LOCATION_LONDON = new LatLng(51.507410, -0.127672);

    public LocationChooserFragment() {
        // Required empty public constructor
    }

    public static LocationChooserFragment newInstance(LatLng currentLocation) {
        if (currentLocation == null) {
            currentLocation = DEFAULT_LOCATION_LONDON;
        }
        LocationChooserFragment fragment = new LocationChooserFragment();
        Bundle args = new Bundle();
        args.putDouble("latitude", currentLocation.latitude);
        args.putDouble("longitude", currentLocation.longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle(R.string.manage_location);

        try {
            locator = new Locator(getActivity(), 500);
        } catch (java.lang.InstantiationException e) {
            Log.e(TAG, "onCreate: ", e);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            Double latitude = bundle.getDouble("latitude", DEFAULT_LOCATION_LONDON.latitude);
            Double longitude = bundle.getDouble("longitude", DEFAULT_LOCATION_LONDON.longitude);
            startUpLocation = new LatLng(latitude, longitude);
        } else {
            startUpLocation = DEFAULT_LOCATION_LONDON;
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLocationChooserBinding binding = FragmentLocationChooserBinding.inflate(inflater, container, false);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.map_container, mapFragment).commit();
        mapFragment.getMapAsync(this);

        binding.btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (locator != null) {
                    locator.disconnectGoogleApiClient();
                }

                locationSelectedListener.onLocationSelected(locationMarker.getPosition());
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }

        LatLng markerLocation = startUpLocation;

        // If the marker location is the default, try and use the user's current location instead
        if (locator != null) {
            Location currentLocation = locator.getLocation();
            if (currentLocation != null) {
                if (markerLocation == DEFAULT_LOCATION_LONDON) {
                    markerLocation = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                }

            }
        }

        locationMarker = googleMap.addMarker(new MarkerOptions()
                .position(markerLocation)
                .draggable(true));

        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            // Need to add an onMarkerDragListener for the markers location to be updated.
            // Don't actually need to add any code to it
            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
            }
        });

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLocation, 12));

        Toast.makeText(getActivity(), getString(R.string.add_location_how_to), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof LocationSelectedListener) {
            locationSelectedListener = (LocationSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LocationSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        locationSelectedListener = null;
    }
}
