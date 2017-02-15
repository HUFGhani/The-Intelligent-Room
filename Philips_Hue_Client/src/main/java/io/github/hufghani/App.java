package io.github.hufghani;

import com.philips.lighting.hue.sdk.PHHueSDK;
import io.github.hufghani.philips.hue.Controller;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        new App();
    }

    public App() {
        PHHueSDK hueSDK = PHHueSDK.create();
        Controller controller = new Controller();
        hueSDK.getNotificationManager().registerSDKListener(controller.getListener());
    }
}
