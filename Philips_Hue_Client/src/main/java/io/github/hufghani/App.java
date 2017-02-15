package io.github.hufghani;

import com.philips.lighting.hue.sdk.PHHueSDK;
import io.github.hufghani.philips.hue.Controller;
import io.github.hufghani.philips.hue.properties.HueProperties;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
        new App();
    }

    public App() {
        HueProperties.loadProperties();
        PHHueSDK hueSDK = PHHueSDK.create();
        Controller controller = new Controller();
        hueSDK.getNotificationManager().registerSDKListener(controller.getListener());
    }
}
