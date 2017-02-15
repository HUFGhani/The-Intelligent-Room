package io.github.hufghani.philips.hue;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by hamzaghani on 14/02/2017.
 */
public class Controller {
    private PHHueSDK phHueSDK;
    private Controller instance;
    private List<PHLight> allLights;
    private PHLightState lightState = new PHLightState();
    String lightIdentifer;
    ObjectMapper mapper = new ObjectMapper();


    public Controller() {
        this.phHueSDK = PHHueSDK.getInstance();
        this.instance = this;

        if (!connectToLastKnownAccessPoint()){
            findBridges();
        }else{
            connectToLastKnownAccessPoint();
        }

    }

    public void findBridges() {
        phHueSDK = PHHueSDK.getInstance();
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
    }

    public PHSDKListener getListener() {
        return listener;
    }

    private boolean connectToLastKnownAccessPoint() {
/*        String username = HueProperties.getUsername();
        String lastIpAddress =  HueProperties.getLastConnectedIP();

        if (username==null || lastIpAddress == null) {
            System.out.println("Missing Last Username or Last IP.  Last known connection not found.");
            return false;
        }
        PHAccessPoint accessPoint = new PHAccessPoint();
        accessPoint.setIpAddress(lastIpAddress);
        accessPoint.setUsername(username);
        phHueSDK.connect(accessPoint);*/

        return true;
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
        return bri >= 0 && bri <= 255;
    }

    private boolean checkSaturationValue(int sat) {
        return sat >= 0 && sat <= 255;
    }

    public void setLight(int red, int green, int blue, int bri, int sat, Boolean isOnorOff) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        allLights = bridge.getResourceCache().getAllLights();
        lightIdentifer = allLights.get(0).getIdentifier();

        if (checkRedValue(red) &&
                checkGreenValue(green) &&
                checkBlueValue(blue) &&
                checkBrighnessValue(bri) &&
                checkSaturationValue(sat) &&
                isOnorOff) {

            lightState.setOn(isOnorOff);
            float xy[] = PHUtilities.calculateXYFromRGB(red, green, blue, null);
            lightState.setX(xy[0]);
            lightState.setY(xy[1]);
            lightState.setBrightness(bri);
            lightState.setSaturation(sat);

        } else if (isOnorOff == false) {
            lightState.setOn(isOnorOff);
        }
        phHueSDK.getSelectedBridge().updateLightState(lightIdentifer, lightState, null);
        // null is passed here as we are not interested in the response from the Bridge.
    }


    public String getStatus() {
        String jsonInString = null;
        float xy[] = new float[]{lightState.getX(), lightState.getY()};
        int rgb = PHUtilities.colorFromXY(xy, PHLight.PHLightColorMode.COLORMODE_XY.getValue());
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = (rgb >> 0) & 0xFF;
        try {
            PhilipsHue philipsHue = new PhilipsHue();
            Light light = new Light();
            Colour colour = new Colour();
            philipsHue.setLight(light);
            light.setOnOff(lightState.isOn());
            light.setBrighness(lightState.getBrightness());
            light.setSaturation(lightState.getSaturation());
            light.setColour(colour);
            colour.setRed(red);
            colour.setGreen(green);
            colour.setBlue(blue);
            jsonInString = mapper.writeValueAsString(philipsHue);
            System.out.println(jsonInString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

    private PHSDKListener listener = new PHSDKListener() {

        @Override
        public void onAccessPointsFound(List<PHAccessPoint> accessPointsList) {
            AccessPointList accessPointList = new AccessPointList(accessPointsList, instance);
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            // Start the Pushlink Authentication.

            phHueSDK.startPushlinkAuthentication(accessPoint);

        }

        @Override
        public void onBridgeConnected(PHBridge bridge, String username) {
            phHueSDK.setSelectedBridge(bridge);
            phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);

            String lastIpAddress = bridge.getResourceCache().getBridgeConfiguration().getIpAddress();
           /* HueProperties.storeUsername(username);
            HueProperties.storeLastIPAddress(lastIpAddress);
            HueProperties.saveProperties();
.
            System.out.println(lastIpAddress);
            System.out.println(username);

            LetStart();*/

        }

        @Override
        public void onCacheUpdated(List<Integer> arg0, PHBridge arg1) {
        }

        @Override
        public void onConnectionLost(PHAccessPoint arg0) {
        }

        @Override
        public void onConnectionResumed(PHBridge arg0) {
        }

        @Override
        public void onError(int code, final String message) {

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
        public void onParsingErrors(List<PHHueParsingError> parsingErrorsList) {
            for (PHHueParsingError parsingError : parsingErrorsList) {
                System.out.println("ParsingError : " + parsingError.getMessage());
            }
        }
    };

}


