package com.project.smarthome.smarthome.Model.SmartHomeService;


import com.project.smarthome.smarthome.Model.LogInRegister.LogInRegisterResponse;
import com.project.smarthome.smarthome.Model.LogInRegister.RegistrationRequest;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface SmartHomeService {
    @GET("users")
    Call<LogInRegisterResponse> logIn(@QueryMap Map<String, String> options);

    @POST("users")
    Call<LogInRegisterResponse> registerUser(@Body RegistrationRequest registrationRequest);
}
