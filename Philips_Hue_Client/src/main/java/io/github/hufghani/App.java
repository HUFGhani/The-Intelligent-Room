package io.github.hufghani;

import io.github.hufghani.mqtt.PhilipsHue_Publish;
import io.github.hufghani.mqtt.PhilipsHue_Subscribe;
import io.github.hufghani.philips.hue.Controller;
import io.github.hufghani.philips.hue.properties.HueProperties;

import java.io.IOException;

public class App
{


    private  boolean auto;
    int r = 0,g = 0,b = 0,br = 0, sa = 0;
    boolean isOnOff = true;


    public static void main( String[] args ) throws IOException {
        new App();
    }

    private App() {
        HueProperties hueProperties = new HueProperties();
        HueProperties.loadProperties();
        Controller controller = new Controller();
        PhilipsHue_Publish philipsHue_Publish = new PhilipsHue_Publish();
        PhilipsHue_Subscribe philipsHue_subscribe = new PhilipsHue_Subscribe();
        philipsHue_subscribe.setTopic(HueProperties.getHouseID());
        philipsHue_Publish.setTopic(HueProperties.getHouseID());
        philipsHue_Publish.publish(controller.getStatus());

        for (;;){
            try {
                philipsHue_subscribe.subscribe();

                if (r != philipsHue_subscribe.getRed() || g != philipsHue_subscribe.getGreen()
                        || b != philipsHue_subscribe.getBlue()
                        || br != philipsHue_subscribe.getBrightness()

                        || sa != philipsHue_subscribe.getSaturation() || isOnOff != philipsHue_subscribe.isOnoff()
                        || auto != philipsHue_subscribe.isAutomated()) {

                    controller.setLight(philipsHue_subscribe.getRed(), philipsHue_subscribe.getGreen(),
                            philipsHue_subscribe.getBlue(), philipsHue_subscribe.getBrightness(),
                            philipsHue_subscribe.getSaturation(), philipsHue_subscribe.isOnoff(),
                            philipsHue_subscribe.isAutomated());

                }

                if (r != philipsHue_subscribe.getRed() || g != philipsHue_subscribe.getGreen()
                        || b != philipsHue_subscribe.getBlue()
                        || br != philipsHue_subscribe.getBrightness()
                        || sa != philipsHue_subscribe.getSaturation() || isOnOff != philipsHue_subscribe.isOnoff() ||
                        auto != philipsHue_subscribe.isAutomated()) {

                    philipsHue_Publish.publish(controller.getStatus());
                }

                r = philipsHue_subscribe.getRed();
                g =philipsHue_subscribe.getGreen();
                b = philipsHue_subscribe.getBlue();
                br =philipsHue_subscribe.getBrightness();
                sa = philipsHue_subscribe.getSaturation();
                isOnOff = philipsHue_subscribe.isOnoff();

                auto = philipsHue_subscribe.isAutomated();

                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
