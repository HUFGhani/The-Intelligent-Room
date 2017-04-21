package com.project.smarthome.smarthome.Presenters.LogInRegister.Shared;


import com.project.smarthome.smarthome.Views.LogInRegister.Shared.LogInRegisterHomeView;

public class LogInRegisterHomePresenter {

    private LogInRegisterHomeView view;

    public LogInRegisterHomePresenter(LogInRegisterHomeView view) {
        this.view = view;
    }

    public void loadView() {
        view.setTitle();
    }

    public void onCameraPermissionResultRecieved(boolean result) {
        view.openQrScannerFragment();
    }

    public void onUserSelectedLogIn() {
        view.openLogInFragment();
    }

    public void onUserSelectedRegister() {
        if (view.checkCameraPermission()) {
            view.openQrScannerFragment();
        } else {
            view.requestCameraPermission();
        }
    }
}
