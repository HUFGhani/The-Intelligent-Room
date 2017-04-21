package com.project.smarthome.smarthome.Views.LogInRegister.LogIn;


public interface LogInDetailsView {
    void setTitle();
    void showEmailError();
    void hideEmailError();
    void showPasswordError();
    void hidePasswordError();
    void showLogInError();
    void showToast(String message);
    void openHomeActivity();
}
