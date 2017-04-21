package com.project.smarthome.smarthome.Utilities;


import java.util.Date;

public class NumberUtilities {

    public static int generateNonSecureRandom() {
        return (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    }
}
