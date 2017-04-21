package com.project.smarthome.smarthome.Presenters.LogInRegister.Shared;


import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Views.LogInRegister.Shared.LogInRegisterView;

public class LogInRegisterPresenter {

    private LogInRegisterView view;
    private ConfigService configService;

    public LogInRegisterPresenter(LogInRegisterView view, ConfigService configService) {
        this.view = view;
        this.configService = configService;
    }

    public void onStart() {
        if (userIsAlreadyLoggedIn()) {
            view.openHomeActivity();
        } else {
            view.showLogInOrRegisterChoice();
        }
    }

    public void onHomeQrCodeEntered(String code) {
        view.showRegistrationDetailsForm(code);
    }

    private boolean userIsAlreadyLoggedIn() {
        return (configService.getHouseConfiguration() != null && configService.getUserPreference() != null);
    }
}
