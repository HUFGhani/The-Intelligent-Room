package com.project.smarthome.smarthome.Presenters.LogInRegister.Register;


import android.util.Log;

import com.project.smarthome.smarthome.Code.MessageQueues.CustomMqttClient;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.LogInRegister.LogInRegisterResponse;
import com.project.smarthome.smarthome.Model.LogInRegister.RegistrationRequest;
import com.project.smarthome.smarthome.Model.SmartHomeService.SmartHomeService;
import com.project.smarthome.smarthome.Model.SmartHomeService.SmartHomeServiceGenerator;
import com.project.smarthome.smarthome.Utilities.TextUtilities;
import com.project.smarthome.smarthome.Views.LogInRegister.Register.RegisterDetailsView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterDetailsPresenter {

    private static final String TAG = RegisterDetailsPresenter.class.getSimpleName();

    private RegisterDetailsView view;
    private ConfigService configService;

    public RegisterDetailsPresenter(RegisterDetailsView view, ConfigService configService) {
        this.view = view;
        this.configService = configService;
    }

    public void onLoad() {
        view.setTitle();
    }

    public void onUserPressedSubmit(RegistrationRequest request) {
        if (validateUserDetails(request)) {
            sendRegistrationRequestToServer(request);
        }
    }

    private void sendRegistrationRequestToServer(RegistrationRequest request) {
        SmartHomeService service = SmartHomeServiceGenerator.generate();
        Call<LogInRegisterResponse> call = service.registerUser(request);

        call.enqueue(new Callback<LogInRegisterResponse>() {
            @Override
            public void onResponse(Call<LogInRegisterResponse> call, Response<LogInRegisterResponse> response) {
                LogInRegisterResponse logInResponse = response.body();
                Log.i(TAG, "onResponse: " + logInResponse.toString());
                processLogInResponse(logInResponse);
            }

            @Override
            public void onFailure(Call<LogInRegisterResponse> call, Throwable t) {
                view.showToast("Error registering");
            }
        });
    }

    private boolean validateUserDetails(RegistrationRequest request) {
        boolean valid = true;

        if (TextUtilities.validatePassword(request.getHomePassword())) {
            view.hideHomePasswordError();
        } else {
            view.showHomePasswordError();
            valid = false;
        }

        if (TextUtilities.validateName(request.getFirstName())) {
            view.hideFirstNameError();
        } else {
            view.showFirstNameError();
            valid = false;
        }

        if (TextUtilities.validateName(request.getLastName())) {
            view.hideLastNameError();
        } else {
            view.showLastNameError();
            valid = false;
        }

        if (TextUtilities.validateEmail(request.getEmail())) {
            view.hideEmailError();
        } else {
            view.showEmailError();
            valid = false;
        }

        if (TextUtilities.validatePassword(request.getUserPassword())) {
            view.hideUserPasswordError();
        } else {
            view.showUserPasswordError();
            valid = false;
        }

        return valid;
    }

    private void processLogInResponse(LogInRegisterResponse logInResponse) {
        if (TextUtilities.isNullOrWhiteSpace(logInResponse.getError())) {
            dealWithSuccessfulResponse(logInResponse);
        } else {
            dealWithFailedResponse(logInResponse);
        }
    }

    private void dealWithFailedResponse(LogInRegisterResponse logInResponse) {
        view.showToast(logInResponse.getError());
    }

    private void dealWithSuccessfulResponse(LogInRegisterResponse logInResponse) {
        configService.saveHouseConfiguration(logInResponse.getHouseConfiguration());
        configService.saveUserPreferences(logInResponse.getUserPreference());
        publishHouseConfiguration(logInResponse);
        publishUserPreferences(logInResponse);
        view.openHomeActivity();
    }

    private void publishUserPreferences(LogInRegisterResponse logInResponse) {
        String houseId = logInResponse.getHouseConfiguration().getHouseId();
        int userId = logInResponse.getUserPreference().getUserId();
        String topic = String.format("%1$s/%2$s/preference", houseId, userId);
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(logInResponse.getUserPreference().toString());
    }

    private void publishHouseConfiguration(LogInRegisterResponse logInResponse) {
        String topic = String.format("%1$s/configuration", logInResponse.getHouseConfiguration().getHouseId());
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(logInResponse.getHouseConfiguration().toString());
    }
}
