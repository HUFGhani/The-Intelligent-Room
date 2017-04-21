package com.project.smarthome.smarthome.Views.DeviceInteraction.DeviceCardList;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.smarthome.smarthome.Code.DeviceUpdates.HeatingDeviceUpdater;
import com.project.smarthome.smarthome.Code.DeviceUpdates.LightingDeviceUpdater;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Presenters.DeviceInteraction.DeviceCardListPresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.RecyclerViews.DeviceAdapter;

import java.util.List;

/**
 * Use the {@link DeviceCardListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeviceCardListFragment
        extends Fragment
        implements LightingDeviceUpdater, HeatingDeviceUpdater, DeviceCardListView{

    private static final String TAG = DeviceCardListFragment.class.getSimpleName();
    private static final String ARG_TITLE = "title";
    private static final String ARG_VIEW_TYPE = "viewType";

    private RecyclerView recyclerView;
    private LightingDeviceUpdater lightingUpdater;
    private HeatingDeviceUpdater heatingUpdater;
    private DeviceAdapter adapter;
    private DeviceCardListPresenter presenter;

    public DeviceCardListFragment() {
        // Required empty public constructor
    }

    public static DeviceCardListFragment newInstance(String title, int viewType) {
        DeviceCardListFragment fragment = new DeviceCardListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putInt(ARG_VIEW_TYPE, viewType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String title = getString(R.string.app_name);
        int viewType = DeviceCardListPresenter.VIEW_ALL_DEVICES;
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE, getString(R.string.app_name));
            viewType = getArguments().getInt(ARG_VIEW_TYPE, DeviceCardListPresenter.VIEW_ALL_DEVICES);
        }

        presenter = new DeviceCardListPresenter(this, getContext(), new ConfigService(getContext()), title, viewType);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.visibilityChanged(true);
        presenter.requestUpdate();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.visibilityChanged(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_card_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.favourites_recycler_view);
        presenter.onStart();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof LightingDeviceUpdater) {
            lightingUpdater = (LightingDeviceUpdater) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement LightingDeviceUpdater");
        }

        if (context instanceof HeatingDeviceUpdater) {
            heatingUpdater = (HeatingDeviceUpdater) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement HeatingDeviceUpdater");
        }
    }

    private void setUpRecyclerView(final DeviceAdapter adapter) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void onUserLightingRequest(LightingDevice device) {
        adapter.notifyDataSetChanged();
        Log.i(TAG, "onUserLightingRequest");
        lightingUpdater.onUserLightingRequest(device);
    }

    @Override
    public void onUserHeatingRequest(HeatingDevice device) {
        adapter.notifyDataSetChanged();
        Log.i(TAG, "onUserHeatingRequest");
        heatingUpdater.onUserHeatingRequest(device);
    }

    @Override
    public void setTitle(String title) {
        getActivity().setTitle(title);
    }

    public void requestUpdate() {
        presenter.requestUpdate();
    }

    @Override
    public void updateView(List<Device> devices) {
        if (devices.size() > 0) {
            adapter = new DeviceAdapter(getActivity(), this, this, devices);
            setUpRecyclerView(adapter);
        }
    }
}
