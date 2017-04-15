package io.github.hufghani;

import io.github.hufghani.philips.hue.Colour;
import io.github.hufghani.philips.hue.Controller;
import io.github.hufghani.philips.hue.Light;
import io.github.hufghani.philips.hue.PhilipsHue;
import io.github.hufghani.philips.hue.properties.HueProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class HueRGBTEST {

 @Test
    public void testHueRed(){
     ObjectMapper mapper = new ObjectMapper();
     HueProperties.loadProperties();
     String jsonInString = null;
     PhilipsHue philipsHue = new PhilipsHue();
     Controller controller = new Controller();
     try {
     Light light = new Light();
     Colour colour = new Colour();
     philipsHue.setLight(light);
     light.setName("Hue color lamp 1");
     light.setOnOff(true);
     light.setBrightness(240);
     light.setSaturation(240);
     light.setColour(colour);
     colour.setRed(255);
     colour.setGreen(0);
     colour.setBlue(0);
     light.setAutomated(true);
          jsonInString = mapper.writeValueAsString(philipsHue);
     } catch (IOException e) {
         e.printStackTrace();
     }
     controller.setLight(255,0,0,240,240,true,true);
     assertEquals(jsonInString,controller.getStatus().toString());
     controller.disconnect();
 }


 @Test
 public void testHueGreen(){
  ObjectMapper mapper = new ObjectMapper();
  HueProperties.loadProperties();
  String jsonInString = null;
  PhilipsHue philipsHue = new PhilipsHue();
  Controller controller = new Controller();
  try {
   Light light = new Light();
   Colour colour = new Colour();
   philipsHue.setLight(light);
   light.setName("Hue color lamp 1");
   light.setOnOff(true);
   light.setBrightness(240);
   light.setSaturation(240);
   light.setColour(colour);
   colour.setRed(0);
   colour.setGreen(255);
   colour.setBlue(0);
   light.setAutomated(true);
   jsonInString = mapper.writeValueAsString(philipsHue);
  } catch (IOException e) {
   e.printStackTrace();
  }
  controller.setLight(0,255,0,240,240,true,true);
  assertEquals(jsonInString,controller.getStatus().toString());
  controller.disconnect();
 }

 @Test
 public void testHueBlue(){
  ObjectMapper mapper = new ObjectMapper();
  HueProperties.loadProperties();
  String jsonInString = null;
  PhilipsHue philipsHue = new PhilipsHue();
  Controller controller = new Controller();
  try {
   Light light = new Light();
   Colour colour = new Colour();
   philipsHue.setLight(light);
   light.setName("Hue color lamp 1");
   light.setOnOff(true);
   light.setBrightness(240);
   light.setSaturation(240);
   light.setColour(colour);
   colour.setRed(0);
   colour.setGreen(0);
   colour.setBlue(255);
   light.setAutomated(true);
   jsonInString = mapper.writeValueAsString(philipsHue);
  } catch (IOException e) {
   e.printStackTrace();
  }
  controller.setLight(0,0,255,240,240,true,true);
  assertEquals(jsonInString,controller.getStatus().toString());
  controller.disconnect();
 }

}
