package io.github.hufghani;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;

import java.util.List;


/**
 * Created by hamzaghani on 14/02/2017.
 */
public class AccessPointList {

    public AccessPointList(final List<PHAccessPoint> accessPointsList, final Controller controller) {

        for (PHAccessPoint accessPoint : accessPointsList) {
            System.out.println(accessPoint.getIpAddress());

        }
        PHHueSDK phHueSDK = PHHueSDK.getInstance();
        phHueSDK.connect(accessPointsList.get(0));


    }
}
