package io.github.hufghani.philips.hue;

import com.philips.lighting.hue.sdk.*;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.*;

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

    public Controller() {
        this.phHueSDK = PHHueSDK.getInstance();
        this.instance = this;

        connectToLastKnownAccessPoint();

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
        if (red >= 0 && red <= 255) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean checkGreenValue(int green) {

        if (green >= 0 && green <= 255) {

            return true;
        } else {
            return false;
        }
    }

    private Boolean checkBlueValue(int blue) {

        if (blue >= 0 && blue <= 255) {

            return true;
        } else {
            return false;
        }
    }

    private boolean checkBrighnessValue(int bri) {

        if (bri >= 0 && bri <= 255) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkSaturationValue(int bri) {

        if (bri >= 0 && bri <= 255) {
            return true;
        } else {
            return false;
        }
    }


    public void setLight(int red, int green, int blue, int bri, int sat, Boolean isOnorOff) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        allLights = bridge.getResourceCache().getAllLights();
        lightIdentifer = allLights.get(0).getIdentifier();

        if (checkRedValue(red) == true &&
                checkGreenValue(green) == true &&
                checkBlueValue(blue) == true &&
                checkBrighnessValue(bri) == true &&
                checkSaturationValue(sat) == true &&
                isOnorOff == true ) {

            lightState.setOn(isOnorOff);
            float xy[] = PHUtilities.calculateXYFromRGB(red, green, blue, null);
            lightState.setX(xy[0]);
            lightState.setY(xy[1]);
            lightState.setBrightness(bri);
            lightState.setSaturation(sat);

        }else if(isOnorOff == false){
            lightState.setOn(isOnorOff);
        }
        phHueSDK.getSelectedBridge().updateLightState(lightIdentifer, lightState, null);    // null is passed here as we are not interested in the response from the Bridge.
    }


    public String getStatus(){
        String json = null;

        boolean onOff = lightState.isOn();



        return json;
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


