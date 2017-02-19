package io.github.hufghani.nest.properties;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by hamzaghani on 16/02/2017.
 */
public class NestProperties {
    private static String fileDirPath = "/etc/home-automation/ha.config";
    static NestConfigProperties nestConfigProperties;

    public NestProperties() {
        loadProperties();
    }

    public static String getHouseID() {
        return nestConfigProperties.getHouseID();
    }


    public static void loadProperties(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if (new File(fileDirPath).exists()) {
                nestConfigProperties = mapper.readValue(new File(fileDirPath), NestConfigProperties.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
