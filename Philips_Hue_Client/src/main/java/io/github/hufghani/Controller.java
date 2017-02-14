package io.github.hufghani;

import com.philips.lighting.hue.sdk.*;
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


        return true;
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


