package com.project.smarthome.smarthome.Code.BarcodeDetection;


import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private BarcodeTracker.Callback mCallback;

    public BarcodeTrackerFactory(BarcodeTracker.Callback callback) {
        mCallback = callback;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        return new BarcodeTracker(mCallback);
    }
}