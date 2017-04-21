package com.project.smarthome.smarthome.Views.LogInRegister.Register;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.project.smarthome.smarthome.Code.BarcodeDetection.BarcodeTracker;
import com.project.smarthome.smarthome.Code.BarcodeDetection.BarcodeTrackerFactory;
import com.project.smarthome.smarthome.R;

import java.io.IOException;

/**
 * Activities that contain this fragment must implement the
 * {@link QRScannerInteractionListener} interface
 * to handle interaction events.
 */
public class QRScannerFragment extends Fragment implements SurfaceHolder.Callback {

    private QRScannerInteractionListener interactionListener;
    private CameraSource cameraSource;
    private BarcodeDetector barcodeDetector;

    public QRScannerFragment() {
        // Required empty public constructor
    }

    public static QRScannerFragment newInstance() {
        return new QRScannerFragment();
    }

    public interface QRScannerInteractionListener {
        void onCodeEntered(String code);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.register));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrscanner, container, false);

        SurfaceView surfaceView = (SurfaceView) view.findViewById(R.id.surface);
        SurfaceHolder mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        final EditText etManualSubmit = (EditText) view.findViewById(R.id.et_qr_manual_input);

        Button btnManualSubmit = (Button) view.findViewById(R.id.btn_qr_manual_submit);
        btnManualSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCodeScanned(etManualSubmit.getText().toString());
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (checkCameraPermission()) {
            barcodeDetector = new BarcodeDetector.Builder(getActivity()).setBarcodeFormats(Barcode.QR_CODE).build();
            BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(new BarcodeTracker.Callback() {
                @Override
                public void onFound(String barcodeValue) {
                    onCodeScanned(barcodeValue);
                }
            });
            barcodeDetector.setProcessor(new MultiProcessor.Builder<>(barcodeFactory).build());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), getString(R.string.qr_scanning_how_to), Toast.LENGTH_LONG).show();

    }

    public void onCodeScanned(String code) {
        if (interactionListener != null) {
            interactionListener.onCodeEntered(code);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof QRScannerInteractionListener) {
            interactionListener = (QRScannerInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement QRScannerInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if (checkCameraPermission()) {
            cameraSource = new CameraSource.Builder(getActivity(), barcodeDetector)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1600, 1024)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(24.0f)
                    .build();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (checkCameraPermission()) {
            try {
                cameraSource.start(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        cameraSource.release();
        cameraSource = null;
    }

    private boolean checkCameraPermission() {
        return (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

}