package io.github.hufghani.philips.hue.properties;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by hamzaghani on 14/02/2017.
 */
public class HueProperties {

    private static String fileDirPath = "/etc/home-automation/ha.config";
    static HueConfig hueConfig;


    public HueProperties() {
        super();
    }

    public static String getUsername() {
        return hueConfig.getUsersname();
    }

    public static void storeUsername(String username) {
        hueConfig.setUsersname(username);
        saveProperties();
    }

    public static String getLastConnectedIP() {
        return hueConfig.getHueLightIP();
    }

    public static void storeLastIPAddress(String ipAddress) {
        hueConfig.setHueLightIP(ipAddress);
        saveProperties();
    }

    public static void loadProperties(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (new File(fileDirPath).exists()) {
                  hueConfig = mapper.readValue(new File(fileDirPath), HueConfig.class);
            }else{
                saveProperties();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveProperties(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (new File(fileDirPath).exists()) {
                mapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileDirPath), HueConfig.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
