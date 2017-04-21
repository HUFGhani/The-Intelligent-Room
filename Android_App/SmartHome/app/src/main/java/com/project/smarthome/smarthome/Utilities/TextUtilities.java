package com.project.smarthome.smarthome.Utilities;

public class TextUtilities {

    public static boolean isNullOrWhiteSpace(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean validateEmail(String email) {
        return (!isNullOrWhiteSpace(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public static boolean validateName(String name) {
        return (!isNullOrWhiteSpace(name) && !containsNumber(name));
    }

    public static boolean containsNumber(String input) {
        return (!isNullOrWhiteSpace(input) && input.matches(".*\\d+.*"));
    }

    public static boolean validatePassword(String password) {
        return (!isNullOrWhiteSpace(password) && password.length() >= 5);
    }

    public static String capitaliseFirstLetter(String input) {
        if (isNullOrWhiteSpace(input)) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
