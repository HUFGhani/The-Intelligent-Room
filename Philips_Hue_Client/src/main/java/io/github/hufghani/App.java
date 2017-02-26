package io.github.hufghani;

import io.github.hufghani.mqtt.PhilipsHue_Publish;
import io.github.hufghani.mqtt.PhilipsHue_Subscribe;
import io.github.hufghani.philips.hue.Controller;
import io.github.hufghani.philips.hue.properties.HueProperties;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException {
        new App();
    }

    private App() {
        HueProperties.loadProperties();
        Controller controller = new Controller();
        PhilipsHue_Publish philipsHue_Publish = new PhilipsHue_Publish();
        PhilipsHue_Subscribe philipsHue_subscribe = new PhilipsHue_Subscribe();
        philipsHue_subscribe.setTopic(HueProperties.getHouseID());
        philipsHue_Publish.setTopic(HueProperties.getHouseID());

        for (;;){
            try {
                philipsHue_Publish.publish(controller.getStatus());
                philipsHue_subscribe.subscribe();

                controller.setLight(philipsHue_subscribe.getRed(),philipsHue_subscribe.getGreen(),
                        philipsHue_subscribe.getBlue(),philipsHue_subscribe.getBrighness(),
                        philipsHue_subscribe.getSaturation(),philipsHue_subscribe.getOnOff());
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
