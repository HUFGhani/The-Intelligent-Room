package com.project.smarthome.smarthome.Views.LogInRegister.Register;


public interface RegisterDetailsView {
    void setTitle();
    void showHomePasswordError();
    void hideHomePasswordError();
    void showFirstNameError();
    void hideFirstNameError();
    void showLastNameError();
    void hideLastNameError();
    void showEmailError();
    void hideEmailError();
    void showUserPasswordError();
    void hideUserPasswordError();
    void showToast(String error);
    void openHomeActivity();
}
