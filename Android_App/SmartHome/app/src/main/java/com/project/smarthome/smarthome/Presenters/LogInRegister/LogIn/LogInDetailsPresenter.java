package com.project.smarthome.smarthome.Presenters.LogInRegister.LogIn;


import com.project.smarthome.smarthome.Code.MessageQueues.CustomMqttClient;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.LogInRegister.LogInRegisterResponse;
import com.project.smarthome.smarthome.Model.LogInRegister.LogInRequest;
import com.project.smarthome.smarthome.Model.SmartHomeService.SmartHomeService;
import com.project.smarthome.smarthome.Model.SmartHomeService.SmartHomeServiceGenerator;
import com.project.smarthome.smarthome.Utilities.TextUtilities;
import com.project.smarthome.smarthome.Views.LogInRegister.LogIn.LogInDetailsView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInDetailsPresenter {

    private static String TAG = LogInDetailsPresenter.class.getSimpleName();

    private LogInDetailsView view;
    private ConfigService configService;

    public LogInDetailsPresenter(LogInDetailsView view, ConfigService configService) {
        this.view = view;
        this.configService = configService;
    }

    public void onStart() {
        view.setTitle();
    }

    public void onLogInPressed(LogInRequest logInRequest) {
        if (validateLogInDetails(logInRequest)) {
            sendLogInRequestToServer(logInRequest);
        }
    }

    private boolean validateLogInDetails(LogInRequest logInRequest) {
        boolean valid = true;

        if (TextUtilities.validateEmail(logInRequest.getEmail())) {
            view.hideEmailError();
        } else {
            view.showEmailError();
            valid = false;
        }

        if (TextUtilities.validatePassword(logInRequest.getPassword())) {
            view.hidePasswordError();
        } else {
            view.showPasswordError();
            valid = false;
        }

        return valid;
    }

    private void sendLogInRequestToServer(LogInRequest logInRequest) {
        Map<String, String> params = new HashMap<>();
        params.put("email", logInRequest.getEmail());
        params.put("password", logInRequest.getPassword());

        SmartHomeService service = SmartHomeServiceGenerator.generate();
        Call<LogInRegisterResponse> call = service.logIn(params);

        call.enqueue(new Callback<LogInRegisterResponse>() {
            @Override
            public void onResponse(Call<LogInRegisterResponse> call, Response<LogInRegisterResponse> response) {
                LogInRegisterResponse logInResponse = response.body();
                processLogInResponse(logInResponse);
            }

            @Override
            public void onFailure(Call<LogInRegisterResponse> call, Throwable t) {
                view.showLogInError();
            }
        });
    }

    private void processLogInResponse(LogInRegisterResponse logInResponse) {
        if (checkLogInSuccess(logInResponse)) {
            dealWithSuccessfulResponse(logInResponse);
        } else {
            if (TextUtilities.isNullOrWhiteSpace(logInResponse.getError())) {
                logInResponse.setError("Invalid email/password combination!");
            }
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

    private boolean checkLogInSuccess(LogInRegisterResponse logInResponse) {
        if (!TextUtilities.isNullOrWhiteSpace(logInResponse.getError())) {
            return false;
        }

        if (logInResponse.getHouseConfiguration() == null || logInResponse.getUserPreference() == null) {
            return false;
        }

        return true;
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
