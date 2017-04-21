package com.project.smarthome.smarthome.Views.LogInRegister.Shared;


public interface LogInRegisterHomeView {
    void setTitle();
    void openLogInFragment();
    void openQrScannerFragment();
    boolean checkCameraPermission();
    void requestCameraPermission();
}
