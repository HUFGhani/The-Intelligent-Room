package com.project.smarthome.smarthome.Model.SmartHomeService;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SmartHomeServiceGenerator {

    // Trailing slash is required
    private static final String BASE_URL = "http://ec2-52-56-203-226.eu-west-2.compute.amazonaws.com/UserAPI/";

    public static SmartHomeService generate() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SmartHomeService.class);
    }
}
