package com.project.smarthome.smarthome.Views.LogInRegister.Shared;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.smarthome.smarthome.Presenters.LogInRegister.Shared.LogInRegisterHomePresenter;
import com.project.smarthome.smarthome.R;
import com.project.smarthome.smarthome.Views.LogInRegister.LogIn.LogInDetailsFragment;
import com.project.smarthome.smarthome.Views.LogInRegister.Register.QRScannerFragment;
import com.project.smarthome.smarthome.databinding.FragmentLogInRegisterHomeBinding;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static android.support.v4.content.PermissionChecker.checkSelfPermission;

public class LogInRegisterHomeFragment extends Fragment implements LogInRegisterHomeView {

    public static final int PERMISSION_REQUEST_CAMERA = 200;
    LogInRegisterHomePresenter presenter;

    public LogInRegisterHomeFragment() {
        // Required empty public constructor
    }

    public static LogInRegisterHomeFragment newInstance() {
        return new LogInRegisterHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new LogInRegisterHomePresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentLogInRegisterHomeBinding binding = FragmentLogInRegisterHomeBinding.inflate(inflater, container, false);

        binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onUserSelectedLogIn();
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onUserSelectedRegister();
            }
        });

        presenter.loadView();

        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            presenter.onCameraPermissionResultRecieved((grantResults[0] == PERMISSION_GRANTED));
        }
    }

    @Override
    public void setTitle() {
        getActivity().setTitle(R.string.app_name);
    }

    @Override
    public void openLogInFragment() {
        loadFragment(LogInDetailsFragment.newInstance());
    }

    @Override
    public void openQrScannerFragment() {
        loadFragment(QRScannerFragment.newInstance());
    }

    @Override
    public boolean checkCameraPermission() {
        return (checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
    }

    private void loadFragment(Fragment fragment) {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }
}
