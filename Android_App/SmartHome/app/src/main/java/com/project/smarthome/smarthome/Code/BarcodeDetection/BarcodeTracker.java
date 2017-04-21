package com.project.smarthome.smarthome.Code.BarcodeDetection;


import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

public class BarcodeTracker extends Tracker<Barcode> {
    private Callback mCallback;

    BarcodeTracker(Callback callback) {
        mCallback = callback;
    }

    @Override
    public void onUpdate(Detector.Detections<Barcode> detectionResults, Barcode barcode) {
        // Access detected barcode values
        mCallback.onFound(barcode.rawValue);
    }

    public interface Callback {
        void onFound(String barcodeValue);
    }
}