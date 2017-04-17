package io.github.hufghani.philips.hue;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.exception.PHHueException;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;
import io.github.hufghani.philips.hue.properties.HueProperties;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.List;

/**
 * Created by hamzaghani on 14/02/2017.
 */
public class Controller implements PHSDKListener {

    private PHHueSDK phHueSDK;

    private PHBridge bridge;
    String lightIdentifer;
    List<PHLight> allLights;
    private  ObjectMapper mapper = new ObjectMapper();
    private Boolean automated;
    int r = 0,g = 0,b = 0,br = 0, sa = 0;
    boolean isOnOff = false;

    public Controller( ) {
        super();
        phHueSDK = PHHueSDK.getInstance();
        try {
            startDiscovery();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            connectToLastKnownAccessPoint();
        }
        try{
            Thread.sleep(5000);
            setLight(255,255,255,254,254, true, true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void startDiscovery() {
        phHueSDK.getNotificationManager().registerSDKListener(this);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }

    public void stopDiscovery() {
        phHueSDK.getNotificationManager().unregisterSDKListener(this);
        phHueSDK.getNotificationManager().cancelSearchNotification();
    }


    private String getUsername() {

        return HueProperties.getUsername();
    }

    private String getIpAddress() {

        return HueProperties.getLastConnectedIP();
    }

    private boolean connectToLastKnownAccessPoint() {
        String username =getUsername();
        String lastIpAddress =  getIpAddress();

        if (username==null || lastIpAddress == null) {
            System.out.println("Missing Last Username or Last IP.  Last known connection not found.");
            return false;
        }
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(lastIpAddress);
        accessPoint.setUsername(username);
        phHueSDK.connect(accessPoint);

        return true;
    }

    @Override
    public void onCacheUpdated(List<Integer> list, PHBridge phBridge) {

    }

    @Override
    public void onBridgeConnected(PHBridge phBridge, String username ) {

        phHueSDK.getInstance().setSelectedBridge(phBridge);
        phHueSDK.enableHeartbeat(phBridge, PHHueSDK.HB_INTERVAL);
        bridge = phBridge;
        String lastIpAddress = bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
        HueProperties.storeUsername(username);
        HueProperties.storeLastIPAddress(lastIpAddress);
//        HueProperties.saveProperties();

    }

    @Override
    public void onAuthenticationRequired(PHAccessPoint phAccessPoint) {
        phHueSDK.startPushlinkAuthentication(phAccessPoint);

    }

    @Override
    public void onAccessPointsFound(List<PHAccessPoint> phAccessPoints) {
        if (phAccessPoints != null && phAccessPoints.size() > 0) {
            phHueSDK.getAccessPointsFound().clear();
            phHueSDK.getAccessPointsFound().addAll(phAccessPoints);

            for (final PHAccessPoint phAccessPoint : phAccessPoints) {
                phAccessPoint.setUsername(getUsername());
                connect(phAccessPoint);
            }
        }
    }

    @Override
    public void onError(int code, String message) {
        if (code == PHHueError.BRIDGE_NOT_RESPONDING) {
            System.out.println("BRIDGE NOT RESPONDING");
        } else if (code == PHMessageType.PUSHLINK_BUTTON_NOT_PRESSED) {
            System.out.println("PUSHLINK BUTTON NOT PRESSED");
        } else if (code == PHMessageType.PUSHLINK_AUTHENTICATION_FAILED) {
            System.out.println("PUSHLINK AUTHENTICATION FAILED");
        } else if (code == PHMessageType.BRIDGE_NOT_FOUND) {
            System.out.println("BRIDGE NOT FOUND");
        }
    }

    @Override
    public void onConnectionResumed(PHBridge phBridge) {
        bridge = phBridge;
        phHueSDK.getLastHeartbeat().put(bridge.getResourceCache().getBridgeConfiguration().getIpAddress(),
                System.currentTimeMillis());
        for (int i = 0; i < phHueSDK.getDisconnectedAccessPoint().size(); i++) {

            if (phHueSDK.getDisconnectedAccessPoint().get(i).getIpAddress().equals(bridge.getResourceCache()
                    .getBridgeConfiguration().getIpAddress())) {
                phHueSDK.getDisconnectedAccessPoint().remove(i);
            }
        }
    }

    @Override
    public void onConnectionLost(PHAccessPoint phAccessPoint) {
        if (!phHueSDK.getDisconnectedAccessPoint().contains(phAccessPoint)) {
            phHueSDK.getDisconnectedAccessPoint().add(phAccessPoint);
        }
    }

    @Override
    public void onParsingErrors(List<PHHueParsingError> list) {

    }

    public void connect(PHAccessPoint accessPoint) {
        try {
            phHueSDK.connect(accessPoint);
        } catch (PHHueException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        phHueSDK.getNotificationManager().unregisterSDKListener(this);
        if (bridge != null) {
            phHueSDK.disableAllHeartbeat();
            try {
                phHueSDK.disconnect(bridge);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bridge = null;
        }
    }

    public void setLight(int red, int green, int blue, int bri, int sat, Boolean isOnorOff,Boolean automated) {
        this.automated = automated;
        bridge = phHueSDK.getSelectedBridge();
        allLights = bridge.getResourceCache().getAllLights();
        lightIdentifer = allLights.get(0).getIdentifier();
        PHLightState lightState = new PHLightState();
//        int r = 0,g = 0,b = 0,br = 0, sa = 0;

        if (r != red || g != green || b != blue || br != bri || sa != sat ) {
            if (checkRedValue(red) &&
                    checkGreenValue(green) &&
                    checkBlueValue(blue) &&
                    checkBrighnessValue(bri) &&
                    checkSaturationValue(sat)) {

                lightState.setOn(isOnorOff);
                System.out.println(isOnorOff + "@@@@@@@@@@@@@@@@");
                float xy[] = PHUtilities.calculateXYFromRGB(red, green, blue, null);
                lightState.setX(xy[0]);
                lightState.setY(xy[1]);
                lightState.setBrightness(bri);
                lightState.setSaturation(sat);

            }

            phHueSDK.getSelectedBridge().updateLightState(lightIdentifer, lightState, null);
            // null is passed here as we are not interested in the response from the Bridge.

            r = red;
            g=green;
            b= blue;
            br= bri;
            sa = sat;
            isOnOff = isOnorOff;
        }
    }

    public String getStatus() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        PHBridgeResourcesCache cache = bridge.getResourceCache();

        List<PHLight> allLights = cache.getAllLights();

        String jsonInString = null;
        for (PHLight hue : allLights) {
         PHLightState state = hue.getLastKnownLightState();
//
//            float xy[] = new float[]{state.getX(), state.getY()};
//            int rgb = PHUtilities.colorFromXY(xy, PHLight.PHLightColorMode.COLORMODE_XY.getValue());
//            int red = (rgb >> 16) & 0xFF;
//            int green = (rgb >> 8) & 0xFF;
//            int blue = (rgb >> 0) & 0xFF;

            try {
                PhilipsHue philipsHue = new PhilipsHue();
                Light light = new Light();
                Colour colour = new Colour();
                philipsHue.setLight(light);
                light.setName(hue.getName());
                light.setOnOff(state.isOn());
                light.setBrightness(state.getBrightness());
                light.setSaturation(state.getSaturation());
                light.setColour(colour);
                colour.setRed(r);
                colour.setGreen(g);
                colour.setBlue(b);
                light.setAutomated(automated);
                jsonInString = mapper.writeValueAsString(philipsHue);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return jsonInString ;
    }

    private Boolean checkRedValue(int red) {
        return red >= 0 && red <= 255;
    }

    private Boolean checkGreenValue(int green) {
        return green >= 0 && green <= 255;
    }

    private Boolean checkBlueValue(int blue) {
        return blue >= 0 && blue <= 255;
    }

    private boolean checkBrighnessValue(int bri) {
        return bri >= 0 && bri <= 254;
    }

    private boolean checkSaturationValue(int sat) {
        return sat >= 0 && sat <= 254;
    }
}