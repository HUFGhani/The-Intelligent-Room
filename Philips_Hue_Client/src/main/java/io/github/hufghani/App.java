package io.github.hufghani;

import com.philips.lighting.hue.sdk.PHHueSDK;
import io.github.hufghani.philips.hue.Controller;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        new App();
    }

    public App() {
        PHHueSDK hueSDK = PHHueSDK.create();
        Controller controller = new Controller();




    }
}
