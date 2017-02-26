package io.github.hufghani.philips.hue.properties;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by hamzaghani on 14/02/2017.
 */
public class HueProperties {

    private static String fileDirPath = "/etc/home-automation/ha.config";
    static HueConfigProperties hueConfigProperties;


    public HueProperties() {
        super();
    }


    public static String getHouseID() {
        return hueConfigProperties.getHouseID();
    }

    public static String getUsername() {
        return hueConfigProperties.getUsersname();
    }

    public static void storeUsername(String username) {
        hueConfigProperties.setUsersname(username);
        //saveProperties();
    }

    public static String getLastConnectedIP() {
        return hueConfigProperties.getHueLightIP();
    }

    public static void storeLastIPAddress(String ipAddress) {
        hueConfigProperties.setHueLightIP(ipAddress);
        //saveProperties();
    }

    public static void loadProperties(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (new File(fileDirPath).exists()) {
                  hueConfigProperties = mapper.readValue(new File(fileDirPath), HueConfigProperties.class);
            }else{
               // saveProperties();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

   /* public static void saveProperties(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (new File(fileDirPath).exists()) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileDirPath), HueConfigProperties.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/



}
